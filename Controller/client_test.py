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

    # JobPayload("my_id2", role="C", data={'hi': "yolo"},
    #         event=PayloadEvent.F_ALARM, start_timestamp=time.time(), interval=timedelta(seconds=2)),

    Payload({'id': "my_id2"}, PayloadEvent.REMOVE, PayloadType.JOB, role="C"),

])
