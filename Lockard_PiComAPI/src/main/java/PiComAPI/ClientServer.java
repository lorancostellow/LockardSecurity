package PiComAPI;

import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadIntr;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class ClientServer extends Server{
    final int PORT = 8000;
    public ClientServer(Handler handler, int listenPort) {
        super(handler, listenPort);
    }

    public Socket createSocket(String host, int port){
        System.out.printf("Creating socket (%s:%d)\n", host, port);
        Socket newSocket = new Socket();

        try {
            //newSocket.setSoLinger(true, 10000);
            newSocket.setReuseAddress(true);
            newSocket.setSoTimeout(ComUtils.TIMEOUT);
            newSocket.connect(new InetSocketAddress(host, port));
            sockets.add(newSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSocket;
    }

    private Socket getSocket(String host, int port){
        // If a connection to the client has already been established
        // then it'll use that socket, else it'll create a socket

        Socket socket = null;
        for (Socket sock : sockets){
            try {
                if (sock.getInetAddress().getCanonicalHostName().equals(host)
                        && sock.isConnected()
                        && sock.getKeepAlive()
                        && sock.getPort() == port){
                    socket = sock;
                    break;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            System.out.println(sock);
        }
        if (socket == null)
            socket = createSocket(host, port);

        assert socket != null : String.format("Unable to create a socket to the host (%s:%d)",
                host, port);

        System.out.println("---------->" + socket);
        return socket;
    }

    public List<PayloadIntr> transaction(List<PayloadIntr> payload, String host, int port) {
        Socket socket = getSocket(host, port);
        ComUtils.sendPayload(socket, payload);
        return ComUtils.receivePayload(socket);
    }
}
