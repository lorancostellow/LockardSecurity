package PiComAPI.Core;

import PiComAPI.Payload.PayloadIntr;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */

public class Server extends Thread {

    public static List<Socket> sockets  = null;
    private ServerSocket serverSocket = null;
    private Socket connectingSocket = null;
    private int port;
    private Handler handler;

    public Server(Handler handler, int port) {
        System.out.printf("Listening on port %d\n", port);
        this.port = port;
        this.handler = handler;
        sockets = Collections.synchronizedList(new LinkedList<Socket>());
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));
            while (true) {
                connectingSocket = serverSocket.accept();
                PiNode node = ComUtils.interrogate(connectingSocket);
                if (node != null){
                    sockets.add(connectingSocket);
                    new Thread(new ClientConnection(connectingSocket, handler)).start();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientConnection implements Runnable {

        Socket clientSocket = null;
        Handler handler;
        PiNode node;

        ClientConnection(Socket socket, Handler handler) {
            System.out.println("New Connection");
            clientSocket = socket;
            this.handler = handler;
        }

        @Override
        public void run() {
            // Keep Connection open from connected client
            while (clientSocket.isConnected() &&
                    !(clientSocket.isInputShutdown() || clientSocket.isOutputShutdown())) {
                List<PayloadIntr> payloads = ComUtils.receivePayload(clientSocket);
                if (payloads!=null && payloads.size() == 1)
                    ComUtils.sendPayload(connectingSocket, handler.process(payloads.get(0), this));
                else {
                    List<PayloadIntr> processed = new LinkedList<>();
                    for (PayloadIntr payload : payloads)
                        processed.add(handler.process(payload, this));
                    ComUtils.sendPayload(connectingSocket, processed);
                }
            }
            sockets.remove(clientSocket); // If the client is dead, its removed from sockets
            System.out.println("Connection Lost");
        }

        public PiNode interrogate(){
            return ComUtils.interrogate(clientSocket);
        }

        public void close() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Connection Closed");
            }
        }
    }
}
