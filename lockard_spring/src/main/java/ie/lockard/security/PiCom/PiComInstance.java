package ie.lockard.security.PiCom;

import ie.lockard.security.PiCom.PayloadModel.Payload;
import ie.lockard.security.PiCom.PayloadModel.PiDataObject;

/**
 * Created by dylan on 25/04/16.
 * Source belongs to Lockard
 */
public class PiComInstance extends PiComServer {
    private static PiComInstance ourInstance = new PiComInstance();

    public static PiComInstance getInstance() {
        return ourInstance;
    }

    private PiComInstance() {
        super(received -> {
            // Handles all the events from the network
            //-------------------------------------------------------------------
            received.setData(new PiDataObject("PROCESSED", "Processing"));

            //-------------------------------------------------------------------
            return received;
        }, 8009);
        startListening();
    }

    public Payload send(Payload payload){
        //Todo: Implement @William
        return null;
    }
}
