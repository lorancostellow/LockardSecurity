import time
from datetime import timedelta

from PiCom.Data import PayloadEvent
from PiCom.Delegation.JOBS import JobPayload, JobPool
from PiCom.Servers.SystemControllerUtils import SYS_Handler


class h (SYS_Handler):
    pass

jb = JobPayload("my_id", "my_name", "C", {'hi': "yolo"},
                PayloadEvent.PANIC, time.time(), timedelta(seconds=10), max_cycles=6)

print(jb.to_dict())
jb2 = JobPayload.from_dict(jb.to_dict())
print(jb2.id)
j = JobPool(h, 1)

j.add_job(jb)

# j.start()
