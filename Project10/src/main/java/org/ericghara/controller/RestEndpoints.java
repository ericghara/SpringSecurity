package org.ericghara.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEndpoints {

    @PostMapping("/test")
    // prefer to use cors configuration customizer
    //@CrossOrigin("*") // all origins, DON'T DO THIS
    public String test() {
        System.out.println("Received a call to /test endpoint");
        return "This was posted from the rest endpoint!";
    }


}
