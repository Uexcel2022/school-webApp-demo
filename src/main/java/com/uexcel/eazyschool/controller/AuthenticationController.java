package com.uexcel.eazyschool.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@EnableAspectJAutoProxy
public class AuthenticationController {
    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLogin(
            @RequestParam(value = "error",required = false) boolean error,
            @RequestParam(value = "logout",required = false) boolean logout,
                         Model model){
        String msg = null;
        if(error){
            msg = "Bad credentials.";
        }
        if(logout){
            msg = "You have logged out successfully.";
        }
        model.addAttribute("msg",msg);
        return "login";
    }

    @RequestMapping("/dashboard")
    public String displayDashboard(Authentication auth, Model user){
        user.addAttribute("username",auth.getName());
        user.addAttribute("authorities",auth.getAuthorities());
        return "dashboard";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication auth){
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }



}
