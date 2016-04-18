package PiComAPI.Payload;
/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *    Payload interface..
 */

public interface PayloadIntr {
    String PAYLOAD_TYPE = "type";
    String PAYLOAD_EVENT = "event";
    String PAYLOAD_DATA = "data";
    String PAYLOAD_ROLE = "role";
    Object getData();
    PayloadType getPayloadType();
    PayloadEvent getPayloadEvent();
    String getRole();
    void setPayloadType(PayloadType type);
    void setPayloadEvent(PayloadEvent event);
    void setData(Object data);
    void setRole(String role);
}
