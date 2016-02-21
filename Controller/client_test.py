from PiCom.Clients import LANClientHandler, LANClient
from PiCom.Data import Payload, print_payload, PayloadType, PayloadEvent


class Handler(LANClientHandler):
    def received(self, req_payload: Payload, res_payload: Payload):
        print("\n\t| REQ:%s\n\t| RSP:%s\n" % (print_payload(req_payload),
                                              print_payload(res_payload)))
        pass


L1 = LANClient("192.168.1.10", 8000, Handler, ignore_errors=False)
#
L1.send([
    #Payload({"id": "alarm"}, PayloadEvent.REMOVE, PayloadType.JOB, role="C"),
    # JobPayload("alarm", role="C", data={'value': True}, name="Turn on",
    #         event=PayloadEvent.PANIC, start_timestamp=time.time(), interval=timedelta(seconds=2)),
    #
    # JobPayload("alarm", role="C", data={'value': False}, name="Turn off",
    #         event=PayloadEvent.PANIC, start_timestamp=time.time(), interval=timedelta(seconds=4)),

    Payload({'value': False}, PayloadEvent.C_ALARM, PayloadType.REQ, role="C"),

])
