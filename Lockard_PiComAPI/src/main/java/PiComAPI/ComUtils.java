package PiComAPI;
import PiComAPI.Payload.*;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *     Helper Class for the Client and Servers for communication
 *     to the raspberry pi
 */
public class ComUtils {

    public static int BUFFER_LENGTH = 1024;
    public static int TIMEOUT = 2000;

    /**
     * Deserializes a JSON string into the Payload Object
     * @param dataStr Json string representing a serialized {@link Payload}
     * @return {@link Payload}
     */
    public synchronized static Payload getDeserializedObject(String dataStr)
            throws MalformedPayloadException {
        JSONObject jsonObject = new JSONObject(dataStr);
        try {
            return new Payload(jsonObject.get(PayloadIntr.PAYLOAD_DATA),
                    jsonObject.getString(PayloadIntr.PAYLOAD_ROLE),
                    PayloadEvent.valueOf(jsonObject.getString(PayloadIntr.PAYLOAD_EVENT)),
                    PayloadType.valueOf(jsonObject.getString(PayloadIntr.PAYLOAD_TYPE)));
        } catch (Exception e){
            throw new MalformedPayloadException(e.getMessage());
        }

    }

    /**
     * Serializes the object and returns the encoded string
     *
     * @param  payload The {@link Payload} to be serialzed
     * @return String containing the serialized object
     */
    public synchronized static byte[] setSerializedObject(PayloadIntr payload) {
        JSONObject objectMap = new JSONObject();
        objectMap.put(PayloadIntr.PAYLOAD_DATA, payload.getData());
        objectMap.put(PayloadIntr.PAYLOAD_ROLE, payload.getRole());
        objectMap.put(PayloadIntr.PAYLOAD_TYPE, payload.getPayloadType());
        objectMap.put(PayloadIntr.PAYLOAD_EVENT, payload.getPayloadEvent());
        return objectMap.toString().getBytes();
    }

    /**
     * Sends a payload through a specified socket
     * @param socket tcp socket
     * @param payload the payload to send
     */
    public static void sendPayload(Socket socket, PayloadIntr payload){
        try {
            socket.getOutputStream().write(
                    ComUtils.setSerializedObject(payload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives the payload from the tcp byte stream
     * @param socket tcp socket
     * @return the received {@link Payload}
     */
    public static Payload receivePayload(Socket socket){

        try {
            char[] charBuffer = new char[BUFFER_LENGTH];
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in.read(charBuffer);
            return new Payload(new String(charBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedPayloadException e) {
            e.printStackTrace();
        }
        return null;
    }
}
