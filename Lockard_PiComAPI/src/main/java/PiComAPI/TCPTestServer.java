package PiComAPI;

import PiComAPI.Core.Client;
import PiComAPI.Core.Configuration;
import PiComAPI.Core.PiNodeEvent;
import PiComAPI.Core.Settings;
import PiComAPI.PayloadModel.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class TCPTestServer {

    public static void main(String[] args) throws Exception, MalformedPayloadException {

        List<Payload> payloads = new ArrayList<>();
        payloads.add(new PayloadObject("1", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("2", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("3", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("4", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("5", "C", PayloadEvent.NODE, PayloadType.REQ));
//
//        payloads.add(new PayloadObject("6", "C", PayloadEvent.PANIC, PayloadType.REQ));
//        payloads.add(new PayloadObject("7", "C", PayloadEvent.H_ALARM, PayloadType.REQ));
//        payloads.add(new PayloadObject("8", "C", PayloadEvent.RSS_ALERT, PayloadType.REQ));
//        payloads.add(new PayloadObject("9", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("10", "C", PayloadEvent.NODE, PayloadType.REQ));
//
//        payloads.add(new PayloadObject("11", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("12", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("13", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("14", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("15", "C", PayloadEvent.NODE, PayloadType.REQ));
//
//        payloads.add(new PayloadObject("16", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("17", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("18", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("19", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("20", "C", PayloadEvent.NODE, PayloadType.REQ));
//
//        payloads.add(new PayloadObject("21", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("22", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("23", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("24", "C", PayloadEvent.NODE, PayloadType.REQ));
//        payloads.add(new PayloadObject("25", "C", PayloadEvent.NODE, PayloadType.REQ));


        // Setup Client
        Configuration configuration = new Configuration(Paths.get("/tmp/test.json"));
        configuration.setSetting(Settings.ALIAS, "Dylan Coss");
        configuration.setSetting(Settings.TOKEN, "A9BACD1FFC");
        configuration.commit(); // Commit changes

        // Create Client with configuration
        LockardClient client = new Client(configuration);
        // set handler
        client.setEventHandler(new PiNodeEvent() {
            @Override
            public void invoked(Payload received) {

            }
        });
        // use client....


        System.out.println(client);


    }
}
