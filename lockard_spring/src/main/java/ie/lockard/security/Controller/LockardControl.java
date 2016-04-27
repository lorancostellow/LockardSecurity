package ie.lockard.security.Controller;

import ie.lockard.security.PiCom.PiComInstanceHandler;
import ie.lockard.security.Service.ResponseService;
import ie.lockard.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Controller
public class LockardControl {


    @Autowired
    public LockardControl(UserService userService, ResponseService responseService) {
        // Starts the listener
        PiComInstanceHandler piComInstanceHandler = new PiComInstanceHandler(responseService);
    }

    //----------------Handle Requests---------------

    @RequestMapping("/")
    public String  indexs(){

        return "index";
    }

    @RequestMapping("/yolo")
    public String  yolo(){

        return "index";
    }
}
