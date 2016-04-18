package PiComAPI;

import PiComAPI.Payload.Payload;
import PiComAPI.Payload.PayloadIntr;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface Handler {
    PayloadIntr process(PayloadIntr receivedPayload, Server.ClientConnection connection);
}
