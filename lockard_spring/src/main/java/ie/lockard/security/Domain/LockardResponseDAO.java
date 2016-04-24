package ie.lockard.security.Domain;

import javax.persistence.*;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "response", schema = "lockard", catalog = "")
public class LockardResponseDAO {
    private int id;
    private String ptoken;
    private String htoken;
    private String sensor;
    private int type;
    private int event;
    private String data;
    private String payload;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ptoken", nullable = false, length = 16)
    public String getPtoken() {
        return ptoken;
    }

    public void setPtoken(String ptoken) {
        this.ptoken = ptoken;
    }

    @Basic
    @Column(name = "htoken", nullable = false, length = 16)
    public String getHtoken() {
        return htoken;
    }

    public void setHtoken(String htoken) {
        this.htoken = htoken;
    }

    @Basic
    @Column(name = "sensor", nullable = false, length = 16)
    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "event", nullable = false)
    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @Basic
    @Column(name = "data", nullable = false, length = -1)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "payload", nullable = false, length = -1)
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockardResponseDAO that = (LockardResponseDAO) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (event != that.event) return false;
        if (ptoken != null ? !ptoken.equals(that.ptoken) : that.ptoken != null) return false;
        if (htoken != null ? !htoken.equals(that.htoken) : that.htoken != null) return false;
        if (sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ptoken != null ? ptoken.hashCode() : 0);
        result = 31 * result + (htoken != null ? htoken.hashCode() : 0);
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + event;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
