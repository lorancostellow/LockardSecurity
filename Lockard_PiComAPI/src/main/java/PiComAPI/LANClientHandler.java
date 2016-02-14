package PiComAPI;

import PiComAPI.Payload.PayloadIntr;

public interface LANClientHandler {
    void received(PayloadIntr req_payload, PayloadIntr res_payload);
}
