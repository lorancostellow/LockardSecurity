package PiComAPI.PayloadModel;

import PiComAPI.Core.PiNode;
import PiComAPI.Core.PiNodeInst;
import org.json.JSONObject;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class SystemPayload {

    public static final String NODE_NAME_FIELD = "name";
    public static final String NODE_ROLE_FIELD = "role";
    public static final String NODE_TOKEN_FIELD = "token";
    public static final String DEVICE_ALIAS_FIELD = "alias";
    public static final String DEVICE_TOKEN_FIELD = "token";
    public static final String NODE_AUTH_FIELD = "auth";
    public static final String NODE_IS_DELEGATOR_FIELD = "isDelegator";
    // Scan Payloads
    public final static PayloadObject NODE_PROBE = new PayloadObject(
            PayloadObject.BLANK_FIELD,
            PayloadObject.WILDCARD,
            PayloadEvent.NODE,
            PayloadType.REQ);
    private final String TOKEN = "your_token";

    // Connection Payloads

    public static PayloadObject DEVICE_CONNECT_REQ(String token, String alias) {
        JSONObject object = new JSONObject();
        object.put(DEVICE_TOKEN_FIELD, token);
        object.put(DEVICE_ALIAS_FIELD, alias);
        return new PayloadObject(
                object.toString(),
                PayloadObject.WILDCARD,
                PayloadEvent.CONNECT,
                PayloadType.REQ);
    }

    public static PiNode NODE_PROBE_RSP(Payload payload)
            throws MalformedPayloadException {
        try {
            if (payload.getPayloadEvent() == PayloadEvent.CONNECT
                    && payload.getPayloadType() == PayloadType.RSP) {
                JSONObject object = new JSONObject(String.valueOf(payload.getData()));
                return new PiNodeInst(
                        object.getString(NODE_TOKEN_FIELD),
                        object.getBoolean(NODE_IS_DELEGATOR_FIELD),
                        object.getString(NODE_ROLE_FIELD),
                        object.getString(NODE_NAME_FIELD),
                        object.getBoolean(NODE_AUTH_FIELD)
                );
            }
            throw new MalformedPayloadException("Probe is malformed: " + payload);
        } catch (Exception e) {
            throw new MalformedPayloadException(e.getMessage());
        }
    }
}
