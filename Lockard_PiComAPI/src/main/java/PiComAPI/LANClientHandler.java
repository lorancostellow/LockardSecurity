package PiComAPI;
import PiComAPI.Payload.PayloadIntr;
/**
 * Author Dylan Coss <dylancoss1@gmail.com>
 *
 * Handles the responses from the server, returning the Payloads that where sent and is received
 */
public interface LANClientHandler {
    /**
     * Invoked when a {@link PayloadIntr} is received
     * @param req_payload requested payload
     * @param res_payload response payload
     */
    void received(PayloadIntr req_payload, PayloadIntr res_payload);
}
