package PiComAPI;

import PiComAPI.Core.PiNode;
import PiComAPI.Core.PiNodeEvent;
import PiComAPI.PayloadModel.Payload;

import java.util.List;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface LockardClient {
    /**
     * Lists nodes on the network
     *
     * @return {@link List<PiNode>} nodes
     */
    List<PiNode> getAllNodes();

    /**
     * Connects to a node on the network
     *
     * @return {@link PiNode} node
     */
    PiNode connect();

    /**
     * Sends a payload to the node
     *
     * @param payload data
     */
    void send(Payload payload);

    /**
     * Sends a list of payloads
     *
     * @param payloads payloads to send
     */
    void send(List<Payload> payloads);

    /**
     * Checks if the system is connected to the Lockard
     * network
     *
     * @return connected
     */
    Boolean isConnected();

    /**
     * checks if the client is on the local network
     *
     * @return is on the home network
     */
    Boolean isLocal();

    /**
     * Retrieves the device token
     *
     * @return Device token
     */
    String getToken();

    /**
     * Sets the event handler for received events from lockard
     *
     * @param eventHandler Handler
     */
    void setEventHandler(PiNodeEvent eventHandler);
}
