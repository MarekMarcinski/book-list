package org.marcinski.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AuthenticationController {

    @PostMapping("/login")
    @ResponseBody
    public Principal login(Principal user){
        return user;
    }
}
