import threading
import time
from datetime import timedelta

from PiCom.Data import PayloadEvent

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


class JobConstraint:
    def __init__(self, start_timestamp, interval: timedelta = None, stop_timestamp=None, run_once=False,
                 ask=False, max_cycles=None):

        self.start = int(start_timestamp)
        self.has_stop = stop_timestamp is not None
        self.has_interval = interval is not None
        self.has_run = False
        self.has_expired = False
        self.has_cycles = max_cycles is not None
        self.max_cycles = max_cycles
        if self.has_interval:
            self.interval = int(interval.total_seconds())
        if self.has_stop:
            if isinstance(stop_timestamp, timedelta):
                self.stop = self.start + stop_timestamp.total_seconds()
            else:
                self.stop = stop_timestamp
        self.run_once = run_once
        self.ask = ask
        self.cycles = 0

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

    def from_json(self, json: str):
        pass


class Job(JobConstraint):
    def __init__(self, id, data, event: PayloadEvent,
                 start_timestamp, interval: timedelta = None,
                 stop_timestamp: timedelta = None, run_once=False,
                 ask=False, max_cycles=None):
        super().__init__(start_timestamp=start_timestamp,
                         interval=interval,
                         stop_timestamp=stop_timestamp,
                         run_once=run_once,
                         ask=ask,
                         max_cycles=max_cycles)

        self.event = event
        self.id = id
        self.data = data

    def content(self):
        return {}


class JobHandler(object):
    def handle(self, job: Job):
        print(job)
        pass


class JobPool(threading.Thread):
    global JOBS

    def __init__(self, handler: JobHandler, interval=1.0):
        super().__init__()
        print("[i] Started Jobs [interval=%d second(s)]" % interval)
        assert handler
        self.interval = interval
        self.handler = handler
        self.JOBS = []
        self.job_count = 0

    def run(self):
        while True:
            self.job_count = len(self.JOBS)
            if self.job_count > 0:

                for j in self.JOBS:
                    if j.runnable():
                        print("[i] Job Running %s" % j.id)
                        self.handler.handle(self, j)
                    if j.has_expired:
                        self.JOBS.remove(j)
            time.sleep(self.interval)

    def add_job(self, job: Job):

        self.JOBS.append(job)

    def get_job_count(self):
        return self.job_count

    def remove_job(self, id):
        for i in self.JOBS:
            if i.id == id:
                self.JOBS.remove(i)
