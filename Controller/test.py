import PiCom.Servers as Servers
from PiCom.Data import EventDomain
from PiCom.Servers.SystemControllerUtils import SYS_Handler


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


lan = Servers.LANServer(Handler, delegator=True, execution_role="C", port=8000)
lan.start()
