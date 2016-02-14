package PiComAPI.Payload;

public enum PayloadType {
    UNK("Unknown Type"),
    REQ("Request"),
    ACK("Acknowledged"),
    RSP("Response");

    String description;
    PayloadType(String s) {
        description = s;
    }
    public String getDescription() {
        return description;
    }
}
