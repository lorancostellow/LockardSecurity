package ie.lockard.security.PiCom.Core;

/**
 * Created by dylan on 20/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class PayloadSendFailed extends Throwable {
    public PayloadSendFailed(String format) {
        super(format);
    }
}
