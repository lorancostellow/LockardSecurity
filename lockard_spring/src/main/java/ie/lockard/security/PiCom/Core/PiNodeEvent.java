package ie.lockard.security.PiCom.Core;
import ie.lockard.security.PiCom.PayloadModel.Payload;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public interface PiNodeEvent {
    Payload invoked(Payload received);
}
