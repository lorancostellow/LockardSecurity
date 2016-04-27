package ie.lockard.security.PiCom.PayloadModel;

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
    private PiData data;
    private String token;

    /**
     * Define your own payload, this can be sent to the raspberry pi's
     * @param data Data to be sent.
     * @param payloadType Describes the "TypeOf" the request being made.
     * @param payloadEvent Describes the "Action" of the request being made.
     * @param role Identifies the server(s) you wish the command to be run on
     * @param token token that identifies the origin of the payload
     */
    public PayloadObject(String token, PiData data, String role, PayloadEvent payloadEvent, PayloadType payloadType) {
        this.data = data;
        this.payloadType = payloadType;
        this.payloadEvent = payloadEvent;
        this.role = role;
        this.token = token;
    }

    public PayloadObject(String token, PiData data, PayloadEvent payloadEvent, PayloadType payloadType) {
        this(token, data, WILDCARD, payloadEvent, payloadType);
    }

    public PayloadObject(String payload) throws MalformedPayloadException {
        JSONObject payloadFields = new JSONObject(payload);
        try {
            payloadType = PayloadType.valueOf((String) payloadFields.get(PAYLOAD_TYPE));
            payloadEvent = PayloadEvent.valueOf((String) payloadFields.get(PAYLOAD_EVENT));
            data = new PiDataObject(String.valueOf(payloadFields.get(PAYLOAD_DATA)));
            role = payloadFields.getString(PAYLOAD_ROLE);
            token = payloadFields.getString(PAYLOAD_TOKEN);

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
     * @return {@link PiData}
     */
    public PiData getData() {
        return data;
    }

    public void setData(PiData data) {
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
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PayloadObject{" +
                "payloadType=" + payloadType +
                ", payloadEvent=" + payloadEvent +
                ", role='" + role + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayloadObject that = (PayloadObject) o;

        if (payloadType != that.payloadType) return false;
        if (payloadEvent != that.payloadEvent) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        int result = payloadType != null ? payloadType.hashCode() : 0;
        result = 31 * result + (payloadEvent != null ? payloadEvent.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
