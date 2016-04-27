package ie.lockard.security.Automation;

import ie.lockard.security.Service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
public class Events {

    private ResponseService responseService;

    @Autowired
    public Events(ResponseService responseService) {
        this.responseService = responseService;
    }

    public static void main(String[] args) {

    }
}
