package PiComAPI;

import PiComAPI.Core.ComUtils;
import PiComAPI.Core.Handler;
import PiComAPI.Core.Server;
import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadIntr;
import PiComAPI.Payload.SystemPayload;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class ClientServer extends Server {

    public ClientServer(Handler handler, int listenPort) {
        super(handler, listenPort);
        start();
    }

    private Socket createSocket(String host, int port){
        System.out.printf("Creating socket (%s:%d)\n", host, port);
        Socket newSocket = new Socket();
        try {
            newSocket.setReuseAddress(true);
            newSocket.setSoTimeout(ComUtils.TIMEOUT);
            newSocket.connect(new InetSocketAddress(host, port));
            sockets.add(newSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSocket;
    }

    public Socket connect(String host, int port){
        Socket socket = null;
        for (Socket sock : sockets){
            InetAddress address = new InetSocketAddress(host, port).getAddress();
            if (sock.getInetAddress().equals(address)) {
                socket = sock;
                break;
            }
        }

        socket = (socket == null) ? createSocket(host, port) : socket;
        // Connection Routine
        System.out.println(ComUtils.receivePayload(socket));
        // Dummy response
        ComUtils.sendPayload(socket, SystemPayload.NODE_PROBE_RSP("Testname", "testRole", true));
        return socket;
    }

    public List<PayloadIntr> send(List<PayloadIntr> payload, String host, int port) {
        Socket socket = connect(host, port);
        ComUtils.sendPayload(socket, payload);
        return ComUtils.receivePayload(socket);
    }

}
