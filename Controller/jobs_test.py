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

jb = JobPayload("my_id", {"value": True}, "C", PayloadEvent.PANIC,
                start_timestamp=start,
                stop_timestamp=end,
                interval=1,
                max_cycles=2,
                run_cycles=False,
                run_once=True,
                cycle_iteration=20,
                on_stop_data={"value": False})

jb2 = JobPayload.from_dict(jb.to_dict())
j = JobPool(h, .5)
j.add_job(jb)

j.start()
