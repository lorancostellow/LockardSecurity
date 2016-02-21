import threading
import time
from datetime import timedelta

from PiCom.Data import get_event_domain, PayloadEvent, PayloadType, PayloadFields, \
    Payload, decode_from_json
from PiCom.Servers.SystemControllerUtils import SYS_Handler, Responder

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
stop_timestamp      -   The stopping time for the job.
interval            -   The time interval the job must run at.
run_once            -   If the job is to run once and expire
ask                 -   If the job is to ask the user before running
max_cycles          -   How many time the job is allowed to run

"""

START_FIELD = 'start'
STOP_FIELD = 'stop'
INTERVAL_FIELD = 'interval'
ASK_FIELD = 'ask'
RUN_ONCE_FIELD = 'run_once'
MAX_CYCLES_FIELD = 'max_cycles'
ID_FIELD = 'id'
NAME_FIELD = 'name'


class JobConstraint:
    def __init__(self, start_timestamp, interval: timedelta = None, stop_timestamp=None, run_once=False,
                 ask=False, max_cycles=None):

        self.start = start_timestamp
        self.stop = stop_timestamp
        self.interval = interval
        self.run_once = run_once
        self.ask = ask
        self.cycles = 0
        self.has_stop = stop_timestamp is not None
        self.has_interval = interval is not None
        self.has_run = False
        self.has_expired = False
        self.has_cycles = max_cycles is not None
        self.max_cycles = max_cycles

        if isinstance(self.start, timedelta):
            self.start = self.start.total_seconds()

        if self.has_interval and isinstance(interval, timedelta):
            self.interval = int(interval.total_seconds())

        if self.has_stop and isinstance(self.stop, timedelta):
            self.stop = int(self.start + self.stop.total_seconds())

        self.start = int(self.start)

    def runnable(self):
        assert self.has_interval
        current_t = int(time.time())
        if self.has_stop and current_t >= self.stop \
                or self.has_expired \
                or (self.has_cycles and self.cycles >= self.max_cycles):
            self.has_expired = True
            return False
        next_t = int(self.start + self.interval)
        in_next = current_t >= next_t
        if self.run_once and in_next:
            self.has_expired = True
            return True
        if in_next:
            self.start = current_t + self.interval
            self.cycles += 1
        return in_next

    @staticmethod
    def from_json_data(json):
        assert isinstance(json, dict)
        return JobConstraint(start_timestamp=json[START_FIELD],
                             interval=json[INTERVAL_FIELD],
                             stop_timestamp=json[STOP_FIELD],
                             run_once=json[RUN_ONCE_FIELD],
                             ask=json[ASK_FIELD],
                             max_cycles=json[MAX_CYCLES_FIELD])

    def constraints_to_json(self):
        return {START_FIELD: self.start,
                INTERVAL_FIELD: self.interval,
                RUN_ONCE_FIELD: self.run_once,
                STOP_FIELD: self.stop,
                MAX_CYCLES_FIELD: self.max_cycles,
                ASK_FIELD: self.ask}


class JobPayload(JobConstraint, Payload):
    def __init__(self, identifier, data, role, event: PayloadEvent,
                 start_timestamp, name=None, interval: timedelta = None,
                 stop_timestamp: timedelta = None, run_once=False,
                 ask=False, max_cycles=None):
        super().__init__(start_timestamp=start_timestamp,
                         interval=interval,
                         stop_timestamp=stop_timestamp,
                         run_once=run_once,
                         ask=ask,
                         max_cycles=max_cycles)
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
                          ask=data[ASK_FIELD],
                          max_cycles=data[MAX_CYCLES_FIELD],
                          interval=data[INTERVAL_FIELD])


class JobPool(threading.Thread, Responder):
    global JOBS

    def __init__(self, handler: SYS_Handler, role, interval=1.0):
        super().__init__()
        self.role = role
        self.handler = handler
        print("[i] Started Jobs [interval=%d second(s)]" % interval)
        self.interval = interval
        self.JOBS = []
        self.job_count = 0

    def run(self):
        while True:
            self.job_count = len(self.JOBS)
            if self.job_count > 0:

                for j in self.JOBS:
                    if j.runnable():
                        j.type = PayloadType.REQ
                        print("[i] Job Running (%s) %s" % (j.id, j.name))
                        self.handler.instruction(self, j.data, j.event, j.domain, Payload.from_dict(j.to_dict()))

                    if j.has_expired:
                        self.JOBS.remove(j)
            time.sleep(self.interval)

    def add_job(self, job: JobPayload):
        print(job.to_dict())
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
