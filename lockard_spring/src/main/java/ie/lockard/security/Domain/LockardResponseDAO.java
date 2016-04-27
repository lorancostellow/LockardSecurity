package ie.lockard.security.Domain;

import javax.persistence.*;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "response", schema = "lockard", catalog = "")
public class LockardResponseDAO {
    private int id;
    private String request;
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
    @Column(name = "request", nullable = false, length = -1)
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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
        if (request != null ? !request.equals(that.request) : that.request != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (request != null ? request.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
