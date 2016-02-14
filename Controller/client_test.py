from PiCom.Clients import LANClientHandler, LANClient
from PiCom.Data import Payload, print_payload, PayloadType, PayloadEvent


class Handler(LANClientHandler):
    def received(self, req_payload: Payload, res_payload: Payload):
        print("\n\t| REQ:%s\n\t| RSP:%s\n" % (print_payload(req_payload),
                                              print_payload(res_payload)))
        pass


L = LANClient("0.0.0.0", 8000, Handler, ignore_errors=False)
#
L.send([

        Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role='C'),
        Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role="A"),
        Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role="B"),
        Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="B"),
        # Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role=None)

        ])

# i = 0
# while True:
#     time.sleep(.05)
#     print(L.send(Payload(i, PayloadEvent.PANIC, PayloadType.REQ, role="A")))
#     L.close_connection()
#
# #
# print((L.send(Payload("ON", PayloadEvent.S_PROBE, PayloadType.REQ, role="B"))))

