package PiComAPI;

import PiComAPI.Payload.Payload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */

public class Server extends Thread{

    public static List<Socket> sockets =
            Collections.synchronizedList(new LinkedList<Socket>());
    private ServerSocket serverSocket = null;
    private Socket connectingSocket = null;
    private int port;
    private Handler handler;
    public Server(Handler handler, int port) {
        this.port = port;
        this.handler = handler;
        System.out.printf("Listening on port %d\n", port);
    }

    @Override
    public void run() {
        super.run();
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                connectingSocket = serverSocket.accept();
                sockets.add(connectingSocket);
                new Thread(new ClientConnection(connectingSocket, handler)).start();
                System.out.println("Current Connections: " + sockets.size());
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

    public void cleanSockets(){
        List<Socket> open = new LinkedList<>();
        for (Socket socket : sockets)
            if (socket.isConnected())
                open.add(socket);
        sockets = Collections.synchronizedList(open);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }

    public class ClientConnection implements Runnable {

        Socket clientSocket = null;
        Handler handler;

        ClientConnection(Socket socket, Handler handler) {
            clientSocket = socket;
            this.handler = handler;
        }

        public void close(){
            if (clientSocket.isConnected())
                sockets.remove(clientSocket);
        }

        public void setHandler(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            Payload payload = ComUtils.receivePayload(clientSocket);
            if (clientSocket.isConnected())
                ComUtils.sendPayload(connectingSocket,handler.process(payload, this));
        }
    }
}
