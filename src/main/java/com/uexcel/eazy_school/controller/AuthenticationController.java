package com.uexcel.eazy_school.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@SessionAttributes({"username","authorities"})
public class AuthenticationController {
    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLogin(
            @RequestParam(value = "error",required = false) boolean error,
            @RequestParam(value = "logout",required = false) boolean logout,
                         Model model){
        String msg = null;
        if(error){
            msg = "Bad login credentials.";
        }
        if(logout){
            msg = "You have logged out successfully.";
        }
        model.addAttribute("msg",msg);
        return "login";
    }

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication auth){
        log.info("user:{}",auth);
        String username = auth.getName();
        String authorities = auth.getAuthorities().toString();
        model.addAttribute("username",username);
        model.addAttribute("authorities",authorities);
        return "dashboard";
    }

}
