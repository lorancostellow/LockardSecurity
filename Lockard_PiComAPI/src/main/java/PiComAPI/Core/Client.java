package PiComAPI.Core;

import PiComAPI.LockardClient;
import PiComAPI.PayloadModel.MalformedPayloadException;
import PiComAPI.PayloadModel.Payload;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class Client implements LockardClient {
    private static Socket socket = null;
    private PiNodeEvent handler;
    private Configuration configuration;

    public Client(Configuration configuration, PiNodeEvent handler) {
        this.configuration = configuration;
        this.handler = handler;
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
            if (isConnected())
                socket.close();
            socket = newSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSocket;
    }

    private void close() {
        if (isConnected())
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /**
     * Establishes a connection to the node
     *
     * @param host node ip
     * @param port node port
     * @return Established Connection to the node
     */
    private Socket initConnect(String host, int port) {
        InetAddress address = new InetSocketAddress(host, port).getAddress();
        if (isConnected() && !socket.getInetAddress().equals(address)) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket = (!isConnected()) ? createSocket(host, port) : socket;
        return socket;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public List<PiNode> getAllNodes() {
        //TODO: Scan network if local
        return new LinkedList<>();
    }

    public PiNode connect() {
        String port = configuration.getSetting(Settings.PORT);
        String host = configuration.getSetting(Settings.DELEGATOR_STATIC_IP);
        PiNode node = null;
        if (!port.isEmpty() && !host.isEmpty()) {
            node = ComUtils.connectionRoutine(initConnect(
                    host, Integer.valueOf(port)),
                    configuration.getSetting(Settings.TOKEN),
                    configuration.getSetting(Settings.ALIAS)
            );
            if (!node.isAuthenticated())
                System.out.println("Not allowed to connect!");
            close();
        }
        return node;
    }

    @Override
    public void send(Payload payload) throws PayloadSendFailed {
        ComUtils.sendPayload(socket, payload);
        try {
            System.out.println(ComUtils.receivePayload(socket));
            handler.invoked(ComUtils.receivePayload(socket));
        } catch (MalformedPayloadException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isConnected() {
        return (socket != null && socket.isConnected());
    }

    @Override
    public Boolean isLocal() {
        //TODO Implement the method;
        // wifi MAC
        String wifiMAC = "C0:FF:EE:C0:FF:EE";
        return (wifiMAC.equals(configuration.getSetting(Settings.ROUTER_MAC)));
    }

    @Override
    public String getToken() {
        return configuration.getSetting(Settings.TOKEN);
    }

    @Override
    public void setEventHandler(PiNodeEvent eventHandler) {
        handler = eventHandler;
    }

    @Override
    public String toString() {
        return "Client " + configuration;
    }
}
