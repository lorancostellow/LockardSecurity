import PiCom.Servers as Servers
from PiCom.Payload import PayloadType, PayloadEvent, Payload

from PiCom.Servers.SystemControllerUtils import GPIO_Handler


class Handler (GPIO_Handler):
    def instruction(self, data, event):
        print("%s/%s" % (data, event))
        self.respond_with_payload(Payload(data, event, PayloadType.RSP))

l = [PayloadEvent.RSS_ALERT]

lan = Servers.LANServer(Handler, delegation_event_whitelist=l)
lan.start()

