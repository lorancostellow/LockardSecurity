package PiComAPI.Core;

import PiComAPI.PayloadModel.Payload;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface PiNodeEvent {
    void invoked(Payload received);
}
