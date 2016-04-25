package ie.lockard.security.PiCom.PayloadModel;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
public interface PiData {
    String PAYLOAD_SENSOR_ID = "sid";
    String PAYLOAD_DATA_FIELD = "pdata";

    String getSensorID();
    Object getData();
    String getSerial();
}
