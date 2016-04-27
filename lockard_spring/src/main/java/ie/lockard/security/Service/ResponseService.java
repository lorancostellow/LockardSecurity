package ie.lockard.security.Service;

import ie.lockard.security.Domain.LockardResponseDAO;
import ie.lockard.security.PiCom.PayloadModel.MalformedPayloadException;
import ie.lockard.security.PiCom.PayloadModel.Payload;
import ie.lockard.security.PiCom.PayloadModel.PayloadObject;
import ie.lockard.security.Repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
@Service
public class ResponseService {

    private ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public Payload getResponse(Payload request) {
        System.out.println("Responding to: " + request);
        for (LockardResponseDAO responseDAO : responseRepository.findAll()) {
            try {
                if (request.equals(new PayloadObject(responseDAO.getRequest())))
                    return new PayloadObject(responseDAO.getPayload());
            } catch (MalformedPayloadException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No Response Found: ");
        return null;
    }
}
