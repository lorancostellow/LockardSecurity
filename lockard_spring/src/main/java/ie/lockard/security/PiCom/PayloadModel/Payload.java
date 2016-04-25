package ie.lockard.security.PiCom.PayloadModel;
/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *    PayloadObject interface..
 */

public interface Payload {
    String PAYLOAD_TYPE = "type";
    String PAYLOAD_EVENT = "event";
    String PAYLOAD_DATA = "data";
    String PAYLOAD_ROLE = "role";
    String PAYLOAD_TOKEN = "token";

    PiData getData();

    void setData(PiData data);

    PayloadType getPayloadType();

    void setPayloadType(PayloadType type);

    PayloadEvent getPayloadEvent();

    void setPayloadEvent(PayloadEvent event);

    String getRole();

    void setRole(String role);

    String getToken();

    void setToken(String token);
}
