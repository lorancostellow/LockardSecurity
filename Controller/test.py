import PiCom.Servers as Servers
from PiCom.Payload import PayloadType, Payload

from PiCom.Servers.SystemControllerUtils import GPIO_Handler


class Handler (GPIO_Handler):
    def instruction(self, data, event):
        print("%s/%s" % (data, event))
        self.respond_with_payload(Payload(data, event, PayloadType.RSP))

lan = Servers.LANServer(Handler, delegator=False)
lan.start()
