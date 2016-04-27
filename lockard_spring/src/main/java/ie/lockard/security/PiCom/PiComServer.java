package ie.lockard.security.PiCom;
/**
 * @Author Dylan Coss <dylancoss1@gmail.com>
 * Source belongs to Lockard
 * Listener for connections between client and server
 */

import ie.lockard.security.PiCom.Core.ComUtils;
import ie.lockard.security.PiCom.Core.PiNodeEvent;
import ie.lockard.security.PiCom.PayloadModel.*;
import ie.lockard.security.Service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMQ;

public class PiComServer {
    private static final int threads = 50;
    private int port;
    private Listener listener;
    private Thread lThreat;
    private ResponseService responseService;

    PiComServer(PiNodeEvent nodeEvent, int port, ResponseService responseService) {
        System.out.println("Listening for events on port: " + port);
        this.port = port;
        this.responseService = responseService;
        listener = new Listener(nodeEvent, port);
    }

    void startListening(){
        if (lThreat==null || !lThreat.isAlive()) {
            lThreat = new Thread(listener);
            lThreat.start();
        } else System.out.println("Thread Running!");
    }

    private void stopListener(){
        if (lThreat!=null && lThreat.isAlive()){
            lThreat.interrupt();
        }
    }

    public int getPort() {
        return port;
    }

    private class Listener implements Runnable{
        PiNodeEvent event;
        String url;
        ZMQ.Socket responder;
        ZMQ.Context context;
        Listener(PiNodeEvent event, int port) {
            context = ZMQ.context(threads);
            responder = context.socket(ZMQ.DEALER);
            this.url = String.format("tcp://*:%d", port);
                    responder.bind(url);
            this.event = event;

        }

        public String getUrl() {
            return url;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String request = responder.recvStr();
                    Payload received = new PayloadObject(request);
                    Payload response = responseService.getResponse(received);
                    if (response!=null)
                        responder.send(ComUtils.setSerializedObject(response),0); // sends the
                    else
                        responder.send(ComUtils.setSerializedObject(
                            event.invoked(received)
                        ),0);
                } catch (Exception | MalformedPayloadException e) {
                    e.printStackTrace();
                }
            }
            responder.close();
            context.term();
        }
    }


//    public static void main(String[] args) throws Exception, MalformedPayloadException {
//
//        PiComServer server = new PiComServer(received -> {
//            System.out.println(received);
//            received.setPayloadType(PayloadType.RSP);
//            received.setData(new PiDataObject("SERVER", "Hi!, We are handling this payload!"));
//            return received;
//        }, 8009);
//
//        server.startListening();
//    }
}