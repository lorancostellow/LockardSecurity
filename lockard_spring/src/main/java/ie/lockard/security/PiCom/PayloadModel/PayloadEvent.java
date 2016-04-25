package ie.lockard.security.PiCom.PayloadModel;
/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 *    Outlines the structure and feature set of the payload
 *    shared between the server and the jump box (delegator)
 *
 *    *NOTE: Will expand as the features develop..
 */
public enum PayloadEvent {

    // ------System management ------
    UNK("Unknown PayloadEvent"),
    NODE("Node info response"),
    CONNECT("Device Connection Request"),
    REGSTN ( "Device Registration"),
    SYSTEM ("System Message"),
    SERVER_ERROR ("PiComServer Error"),
    CLIENT_ERROR ( "Client Error"),
    UNKNOWN_ERROR( "Unknown Error"),
    SUCCESS_SIG ( "Event was successful"),
    FAILED_SIG ("Event was unsuccessful"),

    // ------System feature set ------
    PANIC("Panic Button"),
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
