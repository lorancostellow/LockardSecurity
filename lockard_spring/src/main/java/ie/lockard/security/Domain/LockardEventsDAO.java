package ie.lockard.security.Domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "events", schema = "lockard")
public class LockardEventsDAO {
    private int id;
    private String ptoken;
    private String htoken;
    private String payload;
    private String name;
    private int evenTtype;
    private Timestamp start;
    private Timestamp stop;
    private Timestamp executeAt;
    private Timestamp created;
    private Timestamp lastExecute;
    private String interval;
    private int active;

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
    @Column(name = "payload", nullable = false, length = -1)
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "event_type", nullable = false)
    public int getEvenTtype() {
        return evenTtype;
    }

    public void setEvenTtype(int evenTtype) {
        this.evenTtype = evenTtype;
    }

    @Basic
    @Column(name = "start", nullable = false)
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    @Basic
    @Column(name = "stop", nullable = false)
    public Timestamp getStop() {
        return stop;
    }

    public void setStop(Timestamp stop) {
        this.stop = stop;
    }

    @Basic
    @Column(name = "execute_at", nullable = false)
    public Timestamp getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Timestamp executeAt) {
        this.executeAt = executeAt;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "last_execute", nullable = false)
    public Timestamp getLastExecute() {
        return lastExecute;
    }

    public void setLastExecute(Timestamp lastExecute) {
        this.lastExecute = lastExecute;
    }

    @Basic
    @Column(name = "period", nullable = false, length = 256)
    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockardEventsDAO that = (LockardEventsDAO) o;

        if (id != that.id) return false;
        if (evenTtype != that.evenTtype) return false;
        if (active != that.active) return false;
        if (ptoken != null ? !ptoken.equals(that.ptoken) : that.ptoken != null) return false;
        if (htoken != null ? !htoken.equals(that.htoken) : that.htoken != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (stop != null ? !stop.equals(that.stop) : that.stop != null) return false;
        if (executeAt != null ? !executeAt.equals(that.executeAt) : that.executeAt != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (lastExecute != null ? !lastExecute.equals(that.lastExecute) : that.lastExecute != null) return false;
        if (interval != null ? !interval.equals(that.interval) : that.interval != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ptoken != null ? ptoken.hashCode() : 0);
        result = 31 * result + (htoken != null ? htoken.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + evenTtype;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (stop != null ? stop.hashCode() : 0);
        result = 31 * result + (executeAt != null ? executeAt.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (lastExecute != null ? lastExecute.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        result = 31 * result + active;
        return result;
    }

    @Override
    public String toString() {
        return "LockardEventsDAO{" +
                "id=" + id +
                ", ptoken='" + ptoken + '\'' +
                ", htoken='" + htoken + '\'' +
                ", payload='" + payload + '\'' +
                ", name='" + name + '\'' +
                ", evenTtype=" + evenTtype +
                ", start=" + start +
                ", stop=" + stop +
                ", executeAt=" + executeAt +
                ", created=" + created +
                ", lastExecute=" + lastExecute +
                ", interval='" + interval + '\'' +
                ", active=" + active +
                '}';
    }
}
