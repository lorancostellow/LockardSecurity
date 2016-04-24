import picom
from picom.Data.PayloadObj import Payload
from picom.Data.Structure import PayloadEvent, PayloadType, EventDomain
from picom.Servers.SystemControllerUtils import SYS_Handler


class Handler(SYS_Handler):
    def instruction(self, data, event, domain, payload):
        if domain == EventDomain.GPIO:
            # stuff with the pins
            pass

        elif domain == EventDomain.SOFT:
            # stuff with software
            pass
        print("    [>] data: %s | event: %s (%s) | domain %s (%s) " %
              (data, event.name, event.value, domain.name, domain.value))
        return self.signal_success()


# C = picom.Client("192.168.1.200", 8000)
# C.send(Payload("on", PayloadEvent.HEATING, PayloadType.REQ, "C"))

S = picom.start_lan_server(Handler, execution_role="C", delegator=True)
