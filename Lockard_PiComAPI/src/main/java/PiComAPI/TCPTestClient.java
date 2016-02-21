package PiComAPI;

import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadEvent;
import PiComAPI.Payload.PayloadIntr;
import PiComAPI.Payload.PayloadType;

import java.util.ArrayList;
import java.util.List;


class TCPTestClient
{
    public static void main(String argv[]) throws Exception {

        LANClientHandler handler = new LANClientHandler() {
            public void received(PayloadIntr req_payload, PayloadIntr res_payload) {
                System.out.println(String.format( "Request : %s\nResponse: %s\n", req_payload, res_payload));
            }
        };

        LANClient LANTCPClient = new LANClient("192.168.1.41", 8000);

        LANTCPClient.send(new Payload("hi", "C", PayloadEvent.S_PROBE, PayloadType.REQ), handler);

        List<PayloadIntr> payloads = new ArrayList<PayloadIntr>();
        payloads.add(new Payload("1", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("2", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("3", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("4", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("5", "C", PayloadEvent.S_PROBE, PayloadType.REQ));

        payloads.add(new Payload("6", "C", PayloadEvent.PANIC, PayloadType.REQ));
        payloads.add(new Payload("7", "C", PayloadEvent.H_ALARM, PayloadType.REQ));
        payloads.add(new Payload("8", "C", PayloadEvent.RSS_ALERT, PayloadType.REQ));
        payloads.add(new Payload("9", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("10", "C", PayloadEvent.S_PROBE, PayloadType.REQ));

        payloads.add(new Payload("11", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("12", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("13", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("14", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("15", "C", PayloadEvent.S_PROBE, PayloadType.REQ));

        payloads.add(new Payload("16", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("17", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("18", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("19", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("20", "C", PayloadEvent.S_PROBE, PayloadType.REQ));

        payloads.add(new Payload("21", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("22", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("23", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("24", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
        payloads.add(new Payload("25", "C", PayloadEvent.S_PROBE, PayloadType.REQ));

        LANTCPClient.send(payloads, handler);
    }
}