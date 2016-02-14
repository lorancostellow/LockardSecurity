package PiComAPI;
import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadIntr;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;



public class LANClient {

    private String host;
    private int port;
    private Socket socket;

    public LANClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.socket = new Socket();
        openConnection();
    }

    public void send(List<PayloadIntr> payloads, LANClientHandler handler){
        if (socket.isClosed())
            openConnection();

        for (PayloadIntr payload: payloads) {
            ComUtils.sendPayload(socket, payload);
            handler.received(payload, ComUtils.receivePayload(socket));
        }
    }


    public Payload send(Payload payload, LANClientHandler handler){
        if (socket.isClosed())
            openConnection();
        ComUtils.sendPayload(socket, payload);
        Payload p = ComUtils.receivePayload(socket);
        if (handler != null)
            handler.received(payload, p);
        return p;

    }

    public Payload send(Payload payload){
        return send(payload, null);
    }

    public void openConnection(){
        if (socket.isConnected())
            return;

        try {
            socket.setSoTimeout(ComUtils.TIMEOUT);
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){

        try {
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen(){
        return socket.isConnected();
    }
}

