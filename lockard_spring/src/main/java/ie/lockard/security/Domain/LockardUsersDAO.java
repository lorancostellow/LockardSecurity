package ie.lockard.security.Domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Entity
@Table(name = "users", schema = "lockard")
public class LockardUsersDAO {
    private long id;
    private String token;
    private String firstName;
    private String lastName;
    private String username;
    private Timestamp date;
    private String email;
    private String password;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    @Column(name = "first_name", nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 16)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockardUsersDAO usersDAO = (LockardUsersDAO) o;

        if (id != usersDAO.id) return false;
        if (token != null ? !token.equals(usersDAO.token) : usersDAO.token != null) return false;
        if (firstName != null ? !firstName.equals(usersDAO.firstName) : usersDAO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(usersDAO.lastName) : usersDAO.lastName != null) return false;
        if (username != null ? !username.equals(usersDAO.username) : usersDAO.username != null) return false;
        if (date != null ? !date.equals(usersDAO.date) : usersDAO.date != null) return false;
        if (email != null ? !email.equals(usersDAO.email) : usersDAO.email != null) return false;
        if (password != null ? !password.equals(usersDAO.password) : usersDAO.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LockardUsersDAO{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
