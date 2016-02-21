import time

from PiCom.Data import PayloadEvent, EventDomain, Payload
from PiCom.Delegation.JOBS import JobPayload, JobPool
from PiCom.Servers.SystemControllerUtils import SYS_Handler


class h(SYS_Handler):
    def instruction(self, data, event: PayloadEvent, domain: EventDomain, payload: Payload):
        print("    [>] data: %s | event: %s (%s) | domain %s (%s) " %
              (data, event.name, event.value, domain.name, domain.value))


start = time.time()
end = time.time() + 10

jb = JobPayload("my_id", "data", "C", PayloadEvent.PANIC,
                start_timestamp=start,
                stop_timestamp=end,
                interval=0,
                run_daily=False,
                run_once=False,
                on_stop_data="bye")

print(jb.to_dict())
jb2 = JobPayload.from_dict(jb.to_dict())
print(jb2.id)
j = JobPool(h, 1)

j.add_job(jb)

j.start()
