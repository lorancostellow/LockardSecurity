package PiComAPI.Payload;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONObject;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class SystemPayload {
    public final static Payload NODE_PROBE = new Payload(
            Payload.BLANK_FIELD,
            Payload.WILDCARD,
            PayloadEvent.S_PROBE,
            PayloadType.REQ);
    public static Payload NODE_PROBE_RSP(String name, String role, Boolean isDelegator){
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("role", role);
        object.put("isDelegator", isDelegator);
        return new Payload(
                object.toString(),
                Payload.WILDCARD,
                PayloadEvent.S_PROBE,
                PayloadType.RSP);
    }
}
