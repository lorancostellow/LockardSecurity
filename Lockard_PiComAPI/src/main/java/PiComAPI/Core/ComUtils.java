package PiComAPI.Core;

import PiComAPI.PayloadModel.*;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *     Helper Class for the Client
 *
 */
public class ComUtils {

    public static int TIMEOUT = 2000;
    public static String PAYLOAD_KEY = "payloads";

    /**
     * Deserializes a JSON string into the PayloadObject Object
     * @param dataStr Json string representing a serialized {@link PayloadObject}
     * @return {@link List<Payload>}
     */
    public synchronized static List<Payload> getDeserializedObject(String dataStr)
            throws MalformedPayloadException {
        JSONObject jsonObject = new JSONObject(dataStr);
        List<Payload> payloads = new LinkedList<>();
        try {
            if (jsonObject.has(PAYLOAD_KEY)){
                for (Object p : jsonObject.getJSONArray(PAYLOAD_KEY))
                    payloads.add(new PayloadObject((String) p));
            } else {
                System.out.print("[!] Single payload! Depreciated!");
                payloads.add(new PayloadObject(jsonObject.get(Payload.PAYLOAD_DATA),
                        jsonObject.getString(Payload.PAYLOAD_ROLE),
                        PayloadEvent.valueOf(jsonObject.getString(Payload.PAYLOAD_EVENT)),
                        PayloadType.valueOf(jsonObject.getString(Payload.PAYLOAD_TYPE))));
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
    public synchronized static String setSerializedObject(Payload payload) {
        JSONObject objectMap = new JSONObject();
        objectMap.put(Payload.PAYLOAD_DATA, payload.getData());
        objectMap.put(Payload.PAYLOAD_ROLE, payload.getRole());
        objectMap.put(Payload.PAYLOAD_TYPE, payload.getPayloadType());
        objectMap.put(Payload.PAYLOAD_EVENT, payload.getPayloadEvent());
        return objectMap.toString();
    }

    /**
     * Serializes the object and returns the encoded string
     *
     * @param payloads The {@link List<Payload>} to be serialzed
     * @return String containing the serialized object
     */
    public synchronized static String setSerializedObject(List<Payload> payloads) {
        JSONObject objectMap = new JSONObject();
        List<String> p = new LinkedList<>();
        for (Payload payload : payloads)
            p.add(setSerializedObject(payload));
        objectMap.put(PAYLOAD_KEY, p);
        return objectMap.toString();
    }

    /**
     * Connection routine for connecting to a node
     *
     * @param socket the created socket
     * @param token  the device token
     * @param alias  the username
     * @return {@link PiNode} object
     */
    public static PiNode connectionRoutine(Socket socket, String token, String alias) {
        try {
            // 1. Sends CONNECT Request
            sendPayload(socket, SystemPayload.DEVICE_CONNECT_REQ(token, alias));

            // 2. Get response and check if authenticated
            PiNode node = SystemPayload.NODE_PROBE_RSP(receivePayload(socket).get(0));
            System.out.println("RECEIVED NODE ::" + node);
            return node;
        } catch (MalformedPayloadException | PayloadSendFailed e) {
            e.printStackTrace();
        }
        // Blank node
        return new PiNodeInst("", false, "", "", false);
    }

    /**
     * Sends a payload through a socket
     *
     * @param socket  socket connection
     * @param payload payload to send
     */
    public static void sendPayload(Socket socket, Payload payload) throws PayloadSendFailed {
        List<Payload> list = new LinkedList<>();
        list.add(payload);
        sendPayload(socket, list);
    }

    public static void sendPayload(Socket socket, List<Payload> payloads) throws PayloadSendFailed {
        try {
            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(ComUtils.setSerializedObject(payloads));
            }
            throw new PayloadSendFailed(String.format("%s failed to send with (%s)",
                    payloads, socket));
        } catch (IOException e) {
            throw new PayloadSendFailed(String.format("%s failed to send with (%s)\nERROR: %s",
                    payloads, socket, e.getMessage()));
        }
    }

    public static List<Payload> receivePayload(Socket socket) throws MalformedPayloadException {
        try {
            if (socket != null && socket.isConnected() && !socket.isInputShutdown()) {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                return getDeserializedObject(inputStream.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
