package PiComAPI;

import PiComAPI.Core.Handler;
import PiComAPI.Core.Server;
import PiComAPI.Payload.*;

import java.util.ArrayList;
import java.util.List;

class TCPTestServer {

    public static void main(String[] args) throws Exception, MalformedPayloadException {

        List<PayloadIntr> payloads = new ArrayList<>();
        payloads.add(new Payload("1", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("2", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("3", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("4", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("5", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//
//        payloads.add(new Payload("6", "C", PayloadEvent.PANIC, PayloadType.REQ));
//        payloads.add(new Payload("7", "C", PayloadEvent.H_ALARM, PayloadType.REQ));
//        payloads.add(new Payload("8", "C", PayloadEvent.RSS_ALERT, PayloadType.REQ));
//        payloads.add(new Payload("9", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("10", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//
//        payloads.add(new Payload("11", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("12", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("13", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("14", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("15", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//
//        payloads.add(new Payload("16", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("17", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("18", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("19", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("20", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//
//        payloads.add(new Payload("21", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("22", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("23", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("24", "C", PayloadEvent.S_PROBE, PayloadType.REQ));
//        payloads.add(new Payload("25", "C", PayloadEvent.S_PROBE, PayloadType.REQ));




        ClientServer clientServer = new ClientServer(new Handler() {
            @Override
            public PayloadIntr process(PayloadIntr receivedPayload, Server.ClientConnection connection) {
                System.out.print("Proccessing " + receivedPayload);
                return receivedPayload;
            }
        }, 8000);

        clientServer.connect("192.168.1.200", 8000);

//        for (int i = 0; i != 5; i++) {
//            System.out.println(clientServer.send(payloads, "192.168.1.200", 8000));
//            //Thread.sleep(1000);
////
//        }

    }
}
