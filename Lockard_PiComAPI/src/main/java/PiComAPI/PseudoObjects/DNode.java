package PiComAPI.PseudoObjects;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class DNode {

    private final String token;
    private final String alias;

    public DNode(String token, String alias) {

        this.token = token;
        this.alias = alias;
    }

    public String getToken() {
        return token;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "DNode{" +
                "token='" + token + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
