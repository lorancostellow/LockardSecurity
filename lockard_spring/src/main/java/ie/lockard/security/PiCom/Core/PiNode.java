package ie.lockard.security.PiCom.Core;

/**
 * Created by dylan on 19/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface PiNode {
    Boolean isDelegator();
    String getRole();
    String getName();
    String getToken();

    Boolean isAuthenticated();
}
