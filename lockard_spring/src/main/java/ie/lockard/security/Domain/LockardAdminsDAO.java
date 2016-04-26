package ie.lockard.security.Domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "admins", schema = "lockard")
public class LockardAdminsDAO {
    private long id;
    private long userid;
    private Timestamp time;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid", nullable = false)
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockardAdminsDAO that = (LockardAdminsDAO) o;

        if (id != that.id) return false;
        if (userid != that.userid) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userid ^ (userid >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LockardAdminsDAO{" +
                "id=" + id +
                ", userid=" + userid +
                ", time=" + time +
                '}';
    }
}
