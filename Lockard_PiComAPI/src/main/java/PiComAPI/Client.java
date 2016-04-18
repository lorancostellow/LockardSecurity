package PiComAPI;
import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadIntr;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *     TCP Client to be used to control the raspberry pi's
 *     to the raspberry pi using the common data structures between the two.
 */

public class Client {

    private String host;
    private int port;
    private Socket socket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.socket = new Socket();
        openConnection();
    }

    /**
     * Send a list of payloads, with a handler
     * @param payloads list of {@link Payload}s to send
     * @param handler {@link LANClientHandler} for handling the responses as they arrive
     */
    public void send(List<PayloadIntr> payloads, LANClientHandler handler){
        if (socket.isClosed())
            openConnection();

        for (PayloadIntr payload: payloads) {
            ComUtils.sendPayload(socket, payload);
            handler.received(payload, ComUtils.receivePayload(socket));
        }
    }

    /**
     * Sends a single {@link Payload} with a handler
     * @param payload {@link Payload} to send
     * @param handler for handling the responses as they arrive
     * @return Returns the received Payload that was handled by the handler
     */
    public Payload send(Payload payload, LANClientHandler handler){
        if (socket.isClosed())
            openConnection();
        ComUtils.sendPayload(socket, payload);
        Payload p = ComUtils.receivePayload(socket);
        if (handler != null)
            handler.received(payload, p);
        return p;
    }

    /**
     * Sends a single {@link Payload} without a handler
     * @param payload {@link Payload} to send
     * @return Returns the received {@link Payload}
     */
    public Payload send(Payload payload){
        return send(payload, null);
    }

    /**
     * Opens a connection TCP Connection and returns the socket
     * @return Socket to server
     */
    public Socket openConnection(){
        if (socket.isConnected())
            return socket;
        try {
            socket.setSoTimeout(ComUtils.TIMEOUT);
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    /**
     * Closes the TCP Connection
     */
    public void closeConnection(){

        try {
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection is open
     * @return If the connection is connected
     */
    public boolean isOpen(){
        return socket.isConnected();
    }

}

