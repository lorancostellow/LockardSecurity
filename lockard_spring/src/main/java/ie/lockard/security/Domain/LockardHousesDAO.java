package ie.lockard.security.Domain;

import javax.persistence.*;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "houses", schema = "lockard")
public class LockardHousesDAO {
    private int id;
    private String token;
    private String name;
    private String lon;
    private String lat;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 16)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "lon", nullable = false, length = -1)
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Basic
    @Column(name = "lat", nullable = false, length = -1)
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockardHousesDAO that = (LockardHousesDAO) o;

        if (id != that.id) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lon != null ? !lon.equals(that.lon) : that.lon != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LockardHousesDAO{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
