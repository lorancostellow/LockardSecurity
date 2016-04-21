package PiComAPI.PayloadModel;

import org.json.JSONObject;

/**
 * This class represents the payload sent by the
 * Raspberry Pi's
 */

public class PayloadObject implements Payload {

    public static final String WILDCARD = "<ALL>";
    public static final String BLANK_FIELD = "<BLANK>";
    private PayloadType payloadType ;
    private PayloadEvent payloadEvent;
    private String role;
    private Object data;

    /**
     * Define your own payload, this can be sent to the raspberry pi's
     * @param data Data to be sent.
     * @param payloadType Describes the "TypeOf" the request being made.
     * @param payloadEvent Describes the "Action" of the request being made.
     * @param role Identifies the server(s) you wish the command to be run on
     */
    public PayloadObject(Object data, String role, PayloadEvent payloadEvent, PayloadType payloadType) {
        this.data = data;
        this.payloadType = payloadType;
        this.payloadEvent = payloadEvent;
        this.role = role;
    }

    public PayloadObject(Object data, PayloadEvent payloadEvent, PayloadType payloadType) {
        this(data, WILDCARD, payloadEvent, payloadType);
    }

    public PayloadObject(String payload) throws MalformedPayloadException {
        JSONObject payloadFields = new JSONObject(payload);
        try {
            payloadType = PayloadType.valueOf((String) payloadFields.get(PAYLOAD_TYPE));
            payloadEvent = PayloadEvent.valueOf((String) payloadFields.get(PAYLOAD_EVENT));
            data = payloadFields.get(PAYLOAD_DATA);
            role = payloadFields.getString(PAYLOAD_ROLE);
        } catch (Exception ex){
            throw new MalformedPayloadException(ex.getMessage());
        }
    }

    /**
     * Describes the type of request being made response/request.
     * @return {@link PayloadType}
     */
    public PayloadType getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
    }

    /**
     * Describes the "Action" of the request being made.
     * @return {@link PayloadEvent}
     */
    public PayloadEvent getPayloadEvent() {
        return payloadEvent;
    }

    public void setPayloadEvent(PayloadEvent payloadEvent) {
        this.payloadEvent = payloadEvent;
    }

    /**
     * Reflective of the Data being sent. <br>
     * Note* There are a number of datatypes supported by pyrolite that are returned.
     * @return {@link Object}
     */
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Identifier for the server to check before running/delegating
     * @return
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PayloadObject{" +
                "payloadType=" + payloadType +
                " (" + payloadType.description +
                "), payloadEvent=" + payloadEvent +
                " (" + payloadEvent.decription +
                "), data=" + data +
                ",(" + role +
                ")}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayloadObject payloadObject = (PayloadObject) o;

        return payloadType == payloadObject.payloadType
                && payloadEvent == payloadObject.payloadEvent
                && (data != null ? data.equals(payloadObject.data)
                : payloadObject.data == null);

    }

    @Override
    public int hashCode() {
        int result = payloadEvent != null ? payloadType.hashCode() : 0;
        result = 31 * result + (payloadEvent != null ? payloadEvent.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

}
