import time
from datetime import timedelta

from PiCom.Data import PayloadEvent
from PiCom.Jobs.JOB import JobHandler, JobPool, Job

__version__ = '0.1'
__author__ = "Dylan Coss <dylancoss1@gmail.com>"

"""
 This Package is responsible for scheduling, management and the protocol of
 Jobs on the Delegator & Nodes

:KEY
    NODE: Pi That is'nt a delegator.
    ACK : Acknowledgment Payload.
    SRQ : System Requirement

:REQUEST ALGORITHM
    1.  A Job from the client is requested.

        1.1.  Job is sent to the delegator.
        1.2.  The Delegator then assigns the job to the appropriate NODE.

            1.1.1.  The NODE then adds the job to its job pool.
            1.1.2.  A ACK of the job is sent to the delegator.

        1.3.  The delegator adds the job descriptor to its database.
        1.4.  The delegator receives the ACK from the NODE.
        1.5.  The delegator sends the ACK back to the client.

    2.  The client then receives the ACK of the job.

:CALLBACK ALGORITHM
    1.  A job has been scheduled to start on a NODE.

        1.2.  A ACK is sent to the delegator

              1.1.1  The client then receives the ACK from the delegator.
                     1.1.1.1  Based on the settings, the client will be asked to accept
                              or deny the staring of the job.

              1.1.2  The client sends a ACK of Acknowledgment  to
                     to the delegator.

        1.1.  A ACK of Acknowledgment is sent back to the NODE
              from the delegator.
              SRQ: Job will only start if the ACK is received (based on
                   on the jobs settings [Always ask, Never ask])

    2.  The client is then sent a ACK of the status (started / canceled)

        IF: Started
            2.1  Client is sent a ACK of completion
        IF: Canceled
            2.1  Client is sent a ACK of cancellation

"""


class h(JobHandler):
    def handle(self, job: Job):
        print("\t Triggering: %s" % job.event)


# TEST
P = JobPool(h, interval=1)
# P.start()
t = time.time()
P.add_job(Job("id1", "data1", PayloadEvent.C_ALARM, t, timedelta(seconds=2), stop_timestamp=timedelta(seconds=15),
                  max_cycles=2))

P.add_job(Job("id2", "data2", PayloadEvent.PANIC, t, timedelta(seconds=5), run_once=True))
P.add_job(Job("id3", "data3", PayloadEvent.D_PROBE, t, timedelta(seconds=2), stop_timestamp=timedelta(seconds=10)))
time.sleep(15)
P.add_job(Job("id4", "data3", PayloadEvent.D_PROBE, time.time(), timedelta(seconds=2), stop_timestamp=timedelta(seconds=5)))
