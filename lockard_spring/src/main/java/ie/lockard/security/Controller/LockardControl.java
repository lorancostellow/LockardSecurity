package ie.lockard.security.Controller;

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

    private UserService userService;

    @Autowired
    public LockardControl(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String  indexs(){

        return "index";
    }
}
