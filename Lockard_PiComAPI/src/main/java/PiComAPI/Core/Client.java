package PiComAPI.Core;

import PiComAPI.LockardClient;
import PiComAPI.PayloadModel.MalformedPayloadException;
import PiComAPI.PayloadModel.Payload;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class Client implements LockardClient {
    private static List<Socket> sockets = null;
    private Configuration configuration;

    public Client() {
        sockets = Collections.synchronizedList(new LinkedList<Socket>());
    }

    public Client(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * If on the local network, the client will send to the node
     *
     * @param host node ip
     * @param port node port
     * @return Socket to the node
     */
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

    /**
     * Establishes a connection to the node
     *
     * @param host node ip
     * @param port node port
     * @return Established Connection to the node
     */
    private Socket connect(String host, int port){
        Socket socket = null;
        for (Socket sock : sockets){
            InetAddress address = new InetSocketAddress(host, port).getAddress();
            if (sock.getInetAddress().equals(address)) {
                socket = sock;
                break;
            }
        }
        socket = (socket == null) ? createSocket(host, port) : socket;
        //****************  Connection Routine  *******************
        System.out.println(ComUtils.connectionRoutine(socket, "EPIC_TOKEN", "Samsung Note 3"));

        return socket;
    }

    /**
     * Sends to local node
     *
     * @param payload data
     * @param host    node ip
     * @param port    node port
     * @return response
     * @throws PayloadSendFailed
     * @throws MalformedPayloadException
     */
    private List<Payload> send(List<Payload> payload, String host, int port)
            throws PayloadSendFailed, MalformedPayloadException {
        Socket socket = connect(host, port);
        ComUtils.sendPayload(socket, payload);
        return ComUtils.receivePayload(socket);
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public List<PiNode> getAllNodes() {
        return null;
    }

    @Override
    public PiNode connect() {
        return null;
    }

    @Override
    public void send(Payload payload) {

    }

    @Override
    public void send(List<Payload> payloads) {

    }

    @Override
    public Boolean isConnected() {
        return null;
    }

    @Override
    public Boolean isLocal() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void setEventHandler(PiNodeEvent eventHandler) {

    }

    @Override
    public String toString() {
        return "Client " + configuration;
    }
}
