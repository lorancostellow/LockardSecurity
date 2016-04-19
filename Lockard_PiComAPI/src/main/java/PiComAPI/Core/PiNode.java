package PiComAPI.Core;

import PiComAPI.Payload.Payload;

/**
 * Created by dylan on 19/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface PiNode {
    Boolean getDelegator();
    String getRole();
    String getName();
    Payload sendPayload(Payload payload);
}
