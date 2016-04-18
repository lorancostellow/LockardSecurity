package PiComAPI;

import PiComAPI.Payload.MalformedPayloadException;
import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadEvent;
import PiComAPI.Payload.PayloadType;

class TCPTestServer {

    public static void main(String[] args) throws Exception, MalformedPayloadException {

        Handler handler = new Handler() {
            @Override
            public Payload process(Payload receivedPayload, Server.ClientConnection connection) {
                System.out.println("Processing Payload " + receivedPayload);

                //connection.close(); // close connection else it stays open
                return receivedPayload;
            }
        };


        RoutineHandler routineHandler = new RoutineHandler() {
            @Override
            public Payload process(Payload received, Payload previous) {
                System.out.println(previous);
                if (received.getPayloadEvent() == PayloadEvent.TEXT_ALERT){
                    received.setRole("C");
                    received.setData("on");
                    //received.setPayloadEvent(PayloadEvent.PANIC);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return received;
                }

                return null;
            }
        };

        ClientServer clientServer = new ClientServer(handler, 8000);
        clientServer.start();
//        System.out.println(
//                clientServer.transaction(
//                        new Payload("y1","C", PayloadEvent.PANIC, PayloadType.REQ), "192.168.1.200", 8005));

//        clientServer.routine(routineHandler,
//                new Payload("y1","A", PayloadEvent.TEXT_ALERT, PayloadType.REQ), "192.168.1.200", 8000);
    }
}
