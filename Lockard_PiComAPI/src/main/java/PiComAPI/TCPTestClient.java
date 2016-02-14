package PiComAPI;

import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadEvent;
import PiComAPI.Payload.PayloadIntr;
import PiComAPI.Payload.PayloadType;


class TCPTestClient
{
    public static void main(String argv[]) throws Exception {

        LANClientHandler handler = new LANClientHandler() {
            public void received(PayloadIntr req_payload, PayloadIntr res_payload) {
                System.out.println(String.format( "Request : %s\nResponse: %s\n", req_payload, res_payload));
            }
        };

        LANClient LANTCPClient = new LANClient("0.0.0.0", 8000);

        LANTCPClient.send(new Payload("hi", "A", PayloadEvent.S_PROBE, PayloadType.REQ), handler);

    }
}