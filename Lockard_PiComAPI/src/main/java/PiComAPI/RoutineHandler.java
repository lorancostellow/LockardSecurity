package PiComAPI;

import PiComAPI.Payload.Payload;

/**
 * Created by dylan on 17/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface RoutineHandler {
    Payload process(Payload received, Payload previous);
}
