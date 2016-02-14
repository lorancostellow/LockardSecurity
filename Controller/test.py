import PiCom.Servers as Servers
from PiCom.Servers.SystemControllerUtils import SYS_Handler


class Handler (SYS_Handler):
    def instruction(self, data, event, domain, payload):
        return self.signal_success()


lan = Servers.LANServer(Handler, delegator=False, execution_role="B")
lan.start()
