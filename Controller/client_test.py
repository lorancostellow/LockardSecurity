import time
from datetime import timedelta

from PiCom.Clients import LANClientHandler, LANClient
from PiCom.Data import Payload, print_payload, PayloadType, PayloadEvent
from PiCom.Data.Structure import WILDCARD
from PiCom.Delegation.JOBS import JobPayload


class Handler(LANClientHandler):
    def received(self, req_payload: Payload, res_payload: Payload):
        print("\n\t| REQ:%s\n\t| RSP:%s\n" % (print_payload(req_payload),
                                              print_payload(res_payload)))
        pass


L1 = LANClient("0.0.0.0", 8000, Handler, ignore_errors=False)
#
L1.send([

        Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role='C'),
        Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="A"),
        Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="B"),
        Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="C"),
        Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role=WILDCARD),

        JobPayload("my_id", "my_name", "C", {'hi': "yolo"},
                PayloadEvent.F_ALARM, time.time(), timedelta(seconds=2))
        ])

# L2 = LANClient("0.0.0.0", 8000, Handler, ignore_errors=False)
# #
# L2.send([
#
#         Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role='C'),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="A"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="B"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="C"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role=WILDCARD),
#
#         JobPayload("my_id", "my_name", "my_role", {'hi': "yolo"},
#                 PayloadEvent.PANIC, time.time(), timedelta(seconds=10), max_cycles=6)
#         ])
#
#
# L3 = LANClient("0.0.0.0", 8000, Handler, ignore_errors=False)
# #
# L3.send([
#
#         Payload("Please Mutate", PayloadEvent.RSS_ALERT, PayloadType.REQ, role='C'),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="A"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="B"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role="C"),
#         Payload("Please Mutate", PayloadEvent.S_PROBE, PayloadType.REQ, role=WILDCARD),
#
#         JobPayload("my_id", "my_name", "my_role", {'hi': "yolo"},
#                 PayloadEvent.PANIC, time.time(), timedelta(seconds=10), max_cycles=6)
#         ])
