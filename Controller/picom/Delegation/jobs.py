import threading
import time
from datetime import timedelta

from picom.Data.PayloadObj import Payload, get_event_domain
from picom.Data.Structure import PayloadEvent, PayloadType, PayloadFields
from picom.Servers.SystemControllerUtils import Responder, SYS_Handler
from picom.helpers import decode_from_json

__version__ = '0.1'
__author__ = "Dylan Coss <dylancoss1@gmail.com>"

"""
 This Package is responsible for scheduling, management and the protocol of
 Jobs on the Delegator & Nodes
"""

"""
This class determines when a job is to be run.
Unit of time: seconds

start_timestamp     -   The starting time for the job.
stop_timestamp      -   The stopping time for the job. (optional)
interval            -   The time interval the job must run at. (optional)
run_once            -   If the job is to run once and expire. (optional)
run_cycles          -   Prevents expiry and adds 24 hours(default). (optional)
max_cycles          -   How many time the job is allowed to run (optional)
cycle_iteration     -   The time (seconds) the job should be shifted (optional , default: 24hrs)

                              # Cycles
   >...............................>............................>
current :          :          :          :          :          :
 time  run        run        run        run        run        run
  >>>>>>|>>>>>>>>>>|>>>>>>>>>>|>>>>>>>>>>|>>>>>>>>>>|>>>>>>>>>>|...|.....EXPIRE
   |  start        :          :          :          :         stop |
   |    |          :          :          :          :          |   |
   |    |----------|----------|----------|----------|----------|   |
   |    |      interval   interval   interval   interval       |   |
   |    ________________________________________________________   |
   |                                                               |
   |--------------->---------->---------->---------->---------->---|:START += cycle iteration
   |                            Daily                              |          (default: 24hrs)
   |---------------<----------<----------<----------<----------<---|:STOP  += cycle iteration
                                                                              (default: 24hrs)
"""

START_FIELD = 'start'
STOP_FIELD = 'stop'
INTERVAL_FIELD = 'interval'
RUN_CYCLE = 'run_cycle'
CYCLE_ITERATION = 'cycle_itr'
MAX_CYCLES_FIELD = 'max_cycles'
RUN_ONCE_FIELD = 'run_once'
ID_FIELD = 'id'
NAME_FIELD = 'name'
STOP_DATA_FIELD = 'on_stop'


class JobConstraint:
    def __init__(self, start_timestamp, interval: timedelta = None, stop_timestamp=None, run_once=False,
                 max_cycles=None, run_cycles=False, cycle_iteration=86400):

        self.cycle_iteration = cycle_iteration
        self.run_cycles = run_cycles
        self.interval = interval
        if interval is None:
            self.interval = 0
        elif isinstance(interval, timedelta):
            self.interval = int(interval.total_seconds())
        self.has_interval = self.interval > 0
        self.start = start_timestamp
        self.stop = stop_timestamp
        self.cycles = 0
        self.maxed_cycles = False
        self.has_stop = stop_timestamp is not None
        self.run_once = run_once and not run_cycles
        self.has_run = False
        self.has_expired = False
        self.has_cycles = max_cycles is not None
        self.max_cycles = max_cycles
        self.reached_end = False

        if isinstance(self.start, timedelta):
            self.start = self.start.total_seconds()

        if self.has_stop and isinstance(self.stop, timedelta):
            self.stop = self.start + self.stop.total_seconds()
            self.stop = int(self.stop)
        self.start = int(self.start)
        self.iteration_time = self.start
        # Will on allow run daily if the stop time is within the iteration for the start time
        if self.has_stop:
            self.run_cycles = run_cycles if self.stop < self.start + self.cycle_iteration else False
        else:
            self.run_once = True

    def runnable(self):
        current_t = int(time.time())
        next_t = self.iteration_time
        in_next = current_t >= next_t

        # if the end time is reached
        if self.reached_end and self.run_cycles:
            self.cycles += 1
            self.start += self.cycle_iteration
            if self.has_stop:
                self.stop += self.cycle_iteration

            self.iteration_time = self.start
            self.maxed_cycles = False
            self.reached_end = False
            in_next = False

        self.reached_end = self.has_stop and current_t >= self.stop
        self.maxed_cycles = self.has_cycles and self.cycles >= self.max_cycles

        if self.has_expired or self.maxed_cycles or self.reached_end:
            self.has_expired = not self.run_cycles or self.maxed_cycles
            return False

        if self.run_once and in_next:
            self.reached_end = True
            self.has_expired = not self.run_cycles
            return True

        if in_next:
            self.iteration_time = current_t + self.interval
            return self.has_interval or self.iteration_time == self.start
        return False

    @staticmethod
    def from_json_data(json):
        assert isinstance(json, dict)
        return JobConstraint(start_timestamp=json[START_FIELD],
                             interval=json[INTERVAL_FIELD],
                             stop_timestamp=json[STOP_FIELD],
                             run_once=json[RUN_ONCE_FIELD],
                             max_cycles=json[MAX_CYCLES_FIELD],
                             run_cycles=json[RUN_CYCLE],
                             cycle_iteration=json[CYCLE_ITERATION])

    def constraints_to_json(self):
        return {START_FIELD: self.start,
                INTERVAL_FIELD: self.interval,
                RUN_ONCE_FIELD: self.run_once,
                STOP_FIELD: self.stop,
                MAX_CYCLES_FIELD: self.max_cycles,
                RUN_CYCLE: self.run_cycles,
                CYCLE_ITERATION: self.cycle_iteration}


"""
The payload that is sent to the server

start_timestamp     -   The starting time for the job.
stop_timestamp      -   The stopping time for the job. (optional)
interval            -   The time interval the job must run at. (optional)
run_once            -   If the job is to run once and expire (optional)
run_cycles          -   Prevents expiry and adds 24 hours(default). (optional)
max_cycles          -   How many time the job is allowed to run (optional)
cycle_iteration     -   The time (seconds) the job should be shifted (optional , default: 24hrs

identifier          -   Jobs id, used in the database, and when removing
                        the job
data                -   The data to be used in the instruction
on_stop_data        -   The data to be used in the instruction when the job
                        ends (optional)
role                -   The intended execution role
event               -   The the event to be invoked
name                -   The name of the job (optional)

"""


class JobPayload(JobConstraint, Payload):
    def __init__(self, identifier, data, role, event: PayloadEvent,
                 start_timestamp, on_stop_data=None, name=None, interval=None,
                 stop_timestamp=None, run_once=False, run_cycles=False, cycle_iteration=86400,
                 max_cycles=None):
        super().__init__(start_timestamp=start_timestamp,
                         interval=interval,
                         stop_timestamp=stop_timestamp,
                         run_once=run_once,
                         max_cycles=max_cycles,
                         run_cycles=run_cycles,
                         cycle_iteration=cycle_iteration)
        self.on_stop_data = on_stop_data
        self.has_stop_job = self.on_stop_data is not None
        assert isinstance(event, PayloadEvent)
        self.role = role
        self.type = PayloadType.JOB
        self.name = name
        self.event = event
        self.id = identifier
        self.data = data
        self.domain = get_event_domain(self.event)
        self.job = self.constraints_to_json()

    def to_dict(self):

        self.job[ID_FIELD] = self.id
        self.job[NAME_FIELD] = self.name
        self.job[STOP_DATA_FIELD] = self.on_stop_data
        self.job[PayloadFields.PAYLOAD_DATA.value] = self.data
        self.job[PayloadFields.PAYLOAD_EVENT.value] = self.event.name
        self.job[PayloadFields.PAYLOAD_ROLE.value] = self.role
        self.job[PayloadFields.PAYLOAD_TYPE.value] = self.type.name
        return self.job

    @staticmethod
    def from_dict(data):
        if data is None:
            raise TypeError("The data needed for building the payload is Strings or Dicts.."
                            "\nInputted: %s" % type(data))
        if isinstance(data, str):
            data = decode_from_json(data)
        assert isinstance(data, dict)
        return JobPayload(identifier=data[ID_FIELD],
                          name=data[NAME_FIELD],
                          data=data[PayloadFields.PAYLOAD_DATA.value],
                          event=PayloadEvent[data[PayloadFields.PAYLOAD_EVENT.value]],
                          role=data[PayloadFields.PAYLOAD_ROLE.value],
                          start_timestamp=data[START_FIELD],
                          run_once=data[RUN_ONCE_FIELD],
                          max_cycles=data[MAX_CYCLES_FIELD],
                          interval=data[INTERVAL_FIELD],
                          on_stop_data=data[STOP_DATA_FIELD],
                          run_cycles=data[RUN_CYCLE],
                          cycle_iteration=data[CYCLE_ITERATION])


"""
The job pool thread, used to run the jobs / add / list / count / remove

"""


class JobPool(threading.Thread, Responder):
    global JOBS

    def __init__(self, handler: SYS_Handler, role, interval=1.0):
        super().__init__()
        self.role = role
        self.handler = handler
        self.interval = interval
        self.JOBS = []
        self.job_count = 0
        print("[i] Started Jobs [interval=%d second(s)]" % interval)

    def run(self):
        while True:
            self.job_count = len(self.JOBS)
            if self.job_count > 0:

                for j in self.JOBS:
                    if j.runnable():
                        j.type = PayloadType.REQ
                        print("[i] %s Job Running (%s) %s" % (j.cycles, j.id, j.name))
                        self.handler.instruction(self, j.data, j.event, j.domain, Payload.from_dict(j.to_dict()))
                    if j.reached_end and j.has_stop_job:
                        print("[i] Job On Stop (%s) %s" % (j.id, j.name))
                        self.handler.instruction(self, j.on_stop_data, j.event, j.domain,
                                                 Payload.from_dict(j.to_dict()))
                    if j.has_expired:
                        print("[i] Job Expired (%s) %s" % (j.id, j.name))
                        self.JOBS.remove(j)
            time.sleep(self.interval)

    def add_job(self, job: JobPayload):
        print("[+] New job added (%s) %s [%s]" % (job.id, job.name, job.event))
        self.JOBS.append(job)

    def get_job_count(self):
        return self.job_count

    def remove_job(self, id):
        for i in self.JOBS:
            if i.id == id:
                self.JOBS.remove(i)

    def list_jobs(self):
        return self.JOBS