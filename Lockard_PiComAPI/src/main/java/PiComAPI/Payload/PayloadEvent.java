package PiComAPI.Payload;

public enum PayloadEvent {

    // ------System management ------
    UNK("Unknown PayloadEvent"),
    PANIC ("Panic Button"),
    S_PROBE("Probe for Servers"),
    D_PROBE ("User Device Probe"),
    REGSTN ( "Device Registration"),
    SYSTEM ("System Message"),
    SERVER_ERROR ("Server Error"),
    CLIENT_ERROR ( "Client Error"),
    UNKNOWN_ERROR( "Unknown Error"),
    SUCCESS_SIG ( "Event was successful"),
    FAILED_SIG ("Event was unsuccessful"),

    // ------System feature set ------

    H_ALARM ("House Alarm"),
    F_ALARM ("Fire Alarm") ,
    C_ALARM ("Carbon Monoxide"),
    TEXT_ALERT ("Twitter Text Alert"),
    RSS_ALERT ("RSS Feed Entry"),
    LOCK_STAT ("Lock status");


    String decription;
    PayloadEvent(String s) {
        decription = s;
    }

    public String getDecription() {
        return decription;
    }
}
