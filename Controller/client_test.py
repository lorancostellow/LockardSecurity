from PiCom.Clients.LANClient import LANClientHandler, LANClient
from PiCom.Data import Payload, print_payload, PayloadType, PayloadEvent


class Handler(LANClientHandler):
    def received(self, req_payload: Payload, res_payload: Payload):
        print("\n\t| REQ:%s\n\t| RSP:%s\n" % (print_payload(req_payload),
                                              print_payload(res_payload)))


L = LANClient("0.0.0.0", 8000, Handler)

# print(L.send
#       ([
#
#         Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role='NoRole'),
#         # Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.RSP, role=None),
#         # Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.RSP, role=None),
#         # Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.RSP, role=None)
#
#         ]))

print(L.send(Payload("Please Mutate", PayloadEvent.PANIC, PayloadType.REQ, role=None)))

L.close_connection()
