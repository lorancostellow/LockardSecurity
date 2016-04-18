package PiComAPI;
import PiComAPI.Payload.*;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *     Helper Class for the ClientServer
 *
 */
public class ComUtils {

    public static int BUFFER_LENGTH = 4096;
    public static int TIMEOUT = 2000;
    public static String PAYLOAD_KEY = "payloads";

    /**
     * Deserializes a JSON string into the Payload Object
     * @param dataStr Json string representing a serialized {@link Payload}
     * @return {@link List<PayloadIntr>}
     */
    public synchronized static List<PayloadIntr> getDeserializedObject(String dataStr)
            throws MalformedPayloadException {
        JSONObject jsonObject = new JSONObject(dataStr);
        List<PayloadIntr> payloads = new LinkedList<>();
        try {
            if (jsonObject.has(PAYLOAD_KEY)){
                for (Object p : jsonObject.getJSONArray(PAYLOAD_KEY))
                    payloads.add(new Payload((String) p));
            } else {
                System.out.print("[!] Single payload! Depreciated!");
                payloads.add(new Payload(jsonObject.get(PayloadIntr.PAYLOAD_DATA),
                        jsonObject.getString(PayloadIntr.PAYLOAD_ROLE),
                        PayloadEvent.valueOf(jsonObject.getString(PayloadIntr.PAYLOAD_EVENT)),
                        PayloadType.valueOf(jsonObject.getString(PayloadIntr.PAYLOAD_TYPE))));
            }
        } catch (Exception e){
            throw new MalformedPayloadException(e.getMessage());
        }
        return payloads;
    }

    /**
     * Serializes the object and returns the encoded string
     *
     * @param  payload The {@link Payload} to be serialzed
     * @return String containing the serialized object
     */
    public synchronized static String setSerializedObject(PayloadIntr payload) {
        JSONObject objectMap = new JSONObject();
        objectMap.put(PayloadIntr.PAYLOAD_DATA, payload.getData());
        objectMap.put(PayloadIntr.PAYLOAD_ROLE, payload.getRole());
        objectMap.put(PayloadIntr.PAYLOAD_TYPE, payload.getPayloadType());
        objectMap.put(PayloadIntr.PAYLOAD_EVENT, payload.getPayloadEvent());
        return objectMap.toString();
    }

    public synchronized static String setSerializedObject(List<PayloadIntr> payloads) {
        JSONObject objectMap = new JSONObject();
        List<String> p = new LinkedList<>();
        for (PayloadIntr payload : payloads)
            p.add(setSerializedObject(payload));
        objectMap.put(PAYLOAD_KEY, p);
        return objectMap.toString();
    }

    public static void sendPayload(Socket socket, PayloadIntr payload){
        try {
            String string = ComUtils.setSerializedObject(payload);
            socket.setReceiveBufferSize(string.length() * 2);
            socket.getOutputStream().write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendPayload(Socket socket, List<PayloadIntr> payloads){
        try {
            String string = ComUtils.setSerializedObject(payloads);
            socket.setReceiveBufferSize(string.length() * 2);
            socket.getOutputStream().write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PayloadIntr> receivePayload(Socket socket){
        try {
            int buffSize = socket.getReceiveBufferSize();
            char[] charBuffer = new char[(buffSize > 0) ? buffSize : BUFFER_LENGTH];
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in.read(charBuffer);
            return getDeserializedObject(new String(charBuffer));
        } catch (IOException | MalformedPayloadException e) {
            e.printStackTrace();
        }
        return null;
    }

}
