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
    private String userToken;
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
    @Column(name = "token", nullable = false)
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String token) {
        this.userToken = token;
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

        LockardAdminsDAO adminsDAO = (LockardAdminsDAO) o;

        if (id != adminsDAO.id) return false;
        if (userToken != null ? !userToken.equals(adminsDAO.userToken) : adminsDAO.userToken != null) return false;
        return time != null ? time.equals(adminsDAO.time) : adminsDAO.time == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userToken != null ? userToken.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LockardAdminsDAO{" +
                "id=" + id +
                ", token=" + userToken +
                ", time=" + time +
                '}';
    }
}
