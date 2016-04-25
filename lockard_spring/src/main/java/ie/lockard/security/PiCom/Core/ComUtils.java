package ie.lockard.security.PiCom.Core;

import ie.lockard.security.PiCom.PayloadModel.*;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 * <p>
 * Helper Class for the Client
 */
public class ComUtils {

    public static int TIMEOUT = 2000;
    public static String PAYLOAD_KEY = "payloads";

    /**
     * Deserializes a JSON string into the PayloadObject Object
     *
     * @param dataStr Json string representing a serialized {@link PayloadObject}
     * @return {@link List<Payload>}
     */
    public synchronized static Payload getDeserializedObject(String dataStr)
            throws MalformedPayloadException {
        JSONObject jsonObject = new JSONObject(dataStr);
        try {
            return new PayloadObject(jsonObject.getString(Payload.PAYLOAD_TOKEN),
                    new PiDataObject(jsonObject.getString(Payload.PAYLOAD_DATA)),
                    jsonObject.getString(Payload.PAYLOAD_ROLE),
                    PayloadEvent.valueOf(jsonObject.getString(Payload.PAYLOAD_EVENT)),
                    PayloadType.valueOf(jsonObject.getString(Payload.PAYLOAD_TYPE)));
        } catch (Exception e) {
            throw new MalformedPayloadException(e.getMessage());
        }
    }

    /**
     * Serializes the object and returns the encoded string
     *
     * @param payload The {@link Payload} to be serialzed
     * @return String containing the serialized object
     */
    public synchronized static String setSerializedObject(Payload payload) {
        JSONObject objectMap = new JSONObject();
        objectMap.put(Payload.PAYLOAD_DATA, payload.getData().getSerial());
        objectMap.put(Payload.PAYLOAD_ROLE, payload.getRole());
        objectMap.put(Payload.PAYLOAD_TYPE, payload.getPayloadType());
        objectMap.put(Payload.PAYLOAD_EVENT, payload.getPayloadEvent());
        objectMap.put(Payload.PAYLOAD_TOKEN, payload.getToken());
        return objectMap.toString();
    }



}
