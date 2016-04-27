package ie.lockard.security.PiCom;

import ie.lockard.security.PiCom.PayloadModel.Payload;
import ie.lockard.security.PiCom.PayloadModel.PiDataObject;
import ie.lockard.security.Service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dylan on 25/04/16.
 * Source belongs to Lockard
 */
public class PiComInstanceHandler extends PiComServer {

    @Autowired
    public PiComInstanceHandler(ResponseService responseService) {
        super(received -> {
            System.out.println("--> " + received);
            return received;
        }, 8009, responseService);
        startListening();
    }

    public Payload send(Payload payload){
        //Todo: Implement @William
        return null;
    }
}
