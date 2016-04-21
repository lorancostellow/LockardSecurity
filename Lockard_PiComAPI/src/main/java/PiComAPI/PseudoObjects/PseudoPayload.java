package PiComAPI.PseudoObjects;

import PiComAPI.PayloadModel.*;
import org.json.JSONObject;

import static PiComAPI.PayloadModel.SystemPayload.DEVICE_ALIAS_FIELD;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class PseudoPayload {
    public static final String NODE_AUTH_FIELD = "Auth";
    public static final String NODE_NAME_FIELD = "name";
    public static final String NODE_ROLE_FIELD = "role";
    public static final String NODE_TOKEN_FIELD = "token";
    public static final String NODE_IS_DELEGATOR_FIELD = "isDelegator";
    private final String TOKEN = "Node_Token";

    // Response to CONNECT or S_PROBE
    public static PayloadObject DEVICE_PROBE_RSP(String token, Boolean authed,
                                                 String name, String role, Boolean isDelegator) {
        JSONObject object = new JSONObject();
        object.put(NODE_NAME_FIELD, name);
        object.put(NODE_ROLE_FIELD, role);
        object.put(NODE_IS_DELEGATOR_FIELD, isDelegator);
        object.put(NODE_TOKEN_FIELD, token);
        object.put(NODE_AUTH_FIELD, authed);
        return new PayloadObject(
                object.toString(),
                PayloadObject.WILDCARD,
                PayloadEvent.NODE,
                PayloadType.RSP);
    }

    public static DNode DEVICE(Payload payload) throws MalformedPayloadException {
        try {
            JSONObject object = new JSONObject((String) payload.getData());
            return new DNode(object.getString(NODE_TOKEN_FIELD),
                    object.getString(DEVICE_ALIAS_FIELD));
        } catch (Exception e) {
            throw new MalformedPayloadException(e.getMessage());
        }

    }

}
