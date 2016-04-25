package ie.lockard.security.PiCom.Core;

/**
 * Created by dylan on 19/04/16.
 * Source belongs to Lockard_PiComAPI
 */

public class PiNodeInst implements PiNode {
    private String token;
    private Boolean isDelegator;
    private String role;
    private String name;
    private Boolean authenticated;

    public PiNodeInst(String token, Boolean isDelegator, String role, String name, boolean authenticated) {
        this.isDelegator = isDelegator;
        this.role = role;
        this.name = name;
        this.token = token;
        this.authenticated = authenticated;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Boolean isDelegator() {
        return isDelegator;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiNodeInst that = (PiNodeInst) o;

        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PiNodeInst{" +
                "token='" + token + '\'' +
                ", isDelegator=" + isDelegator +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }
}
