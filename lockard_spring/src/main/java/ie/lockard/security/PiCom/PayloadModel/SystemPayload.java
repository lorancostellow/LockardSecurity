package ie.lockard.security.PiCom.PayloadModel;

import org.json.JSONObject;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */

public class SystemPayload {
    public static Payload PAYLOAD_PARSE_ERROR(String error){
        return new PayloadObject("ERROR",
                new PiDataObject("ERROR", error),
                PayloadEvent.SERVER_ERROR,
                PayloadType.RSP);
    }
}
