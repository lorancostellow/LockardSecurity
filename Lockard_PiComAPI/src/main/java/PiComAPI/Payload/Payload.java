package PiComAPI.Payload;

import org.json.JSONObject;

/**
 * This class represents the payload sent by the
 * Raspberry Pi's
 */

public class Payload implements PayloadIntr {

    private PayloadType payloadType ;
    private PayloadEvent payloadEvent;
    private String role;
    private Object data;
    public static String WILDCARD = "<ALL>";
    public static String BLANK_FIELD = "<BLANK>";

    /**
     * Define your own payload, this can be sent to the raspberry pi's
     * @param data Data to be sent.
     * @param payloadType Describes the "TypeOf" the request being made.
     * @param payloadEvent Describes the "Action" of the request being made.
     * @param role Identifies the server(s) you wish the command to be run on
     */
    public Payload(Object data, String role, PayloadEvent payloadEvent, PayloadType payloadType){
        this.data = data;
        this.payloadType = payloadType;
        this.payloadEvent = payloadEvent;
        this.role = role;
    }

    public Payload(Object data, PayloadEvent payloadEvent, PayloadType payloadType){
        this(data, WILDCARD, payloadEvent, payloadType);
    }

    public Payload(String payload) throws MalformedPayloadException {
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

    /**
     * Describes the "Action" of the request being made.
     * @return {@link PayloadEvent}
     */
    public PayloadEvent getPayloadEvent() {
        return payloadEvent;
    }

    /**
     * Reflective of the Data being sent. <br>
     * Note* There are a number of datatypes supported by pyrolite that are returned.
     * @return {@link Object}
     */
    public Object getData() {
        return data;
    }

    /**
     * Identifier for the server to check before running/delegating
     * @return
     */
    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Payload{" +
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

        Payload payload = (Payload) o;

        return payloadType == payload.payloadType
                && payloadEvent == payload.payloadEvent
                && (data != null ? data.equals(payload.data)
                : payload.data == null);

    }

    @Override
    public int hashCode() {
        int result = payloadEvent != null ? payloadType.hashCode() : 0;
        result = 31 * result + (payloadEvent != null ? payloadEvent.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

}
