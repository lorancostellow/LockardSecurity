package PiComAPI;

import PiComAPI.Payload.Payload;

import java.net.Socket;
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

    private Socket getSocket(String host, int port){
        // If a connection to the client has already been established
        // then it'll use that socket, else it'll create a socket

        Socket socket = null;
        for (Socket sock : sockets){
            if (sock.getInetAddress().getCanonicalHostName().equals(host)
                    && sock.isConnected()
                    && sock.getLocalPort() == port){
                socket = sock;
                break;
            }
        }
        if (socket == null){
            System.out.println("Creating Socket...");
            socket = new Client(host, port).openConnection();
        }

        assert socket != null : String.format("Unable to create a socket to the host (%s:%d)",
                host, port);

        System.out.println("---------->" + socket);
        return socket;
    }

    public Payload transaction(Payload payload, String host, int port) {
        Socket socket = getSocket(host, port);
        ComUtils.sendPayload(socket, payload);
        return ComUtils.receivePayload(socket);
    }

    public Payload transaction(Payload payload, String host) {
        return transaction(payload, host, PORT);
    }

    public Payload routine (RoutineHandler handler, Payload payload, String host, int port){
        Payload processedPayload = handler.process(transaction(payload, host, port), payload);
        if (processedPayload != null)
            routine(handler, processedPayload, host, port);
        return payload;
    }
}
