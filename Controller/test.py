import PiCom.Servers as Servers

from PiCom.Servers.SystemControllerUtils import SYS_Handler


class Handler (SYS_Handler):
    def instruction(self, data, event, domain):
        self.signal_success()
        return

lan = Servers.LANServer(Handler, delegator=False)
lan.start()
