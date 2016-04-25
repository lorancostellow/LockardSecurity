package ie.lockard.security.PiCom.PayloadModel;
import org.json.JSONObject;


/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
public class PiDataObject implements PiData {
    private String sensorID;
    private Object data;

    public PiDataObject(String sensorID, Object data) {
        this.sensorID = sensorID;
        this.data = data;
    }

    public PiDataObject(String json) {
        JSONObject object = new JSONObject(json);
        sensorID = object.getString(PAYLOAD_SENSOR_ID);
        data = object.get(PAYLOAD_DATA_FIELD);
    }

    @Override
    public String getSensorID() {
        return sensorID;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String getSerial() {
        JSONObject objectMap = new JSONObject();
        objectMap.put(PAYLOAD_SENSOR_ID, sensorID);
        objectMap.put(PAYLOAD_DATA_FIELD, data);
        return objectMap.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiDataObject that = (PiDataObject) o;

        return sensorID != null ? sensorID.equals(that.sensorID) : that.sensorID == null;

    }

    @Override
    public int hashCode() {
        return sensorID != null ? sensorID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PiData{" +
                "sensorID='" + sensorID + '\'' +
                ", data=" + data +
                '}';
    }
}
