package com.uexcel.eazyschool.controller;


import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PersonRepository personRepository;

    @Autowired
    public AuthenticationController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLogin(
            @RequestParam(value = "error",required = false) boolean error,
            @RequestParam(value = "logout",required = false) boolean logout,
            @RequestParam(value = "register",required = false) boolean register,
                         Model model){
        String msg = null;
        if(error){
            msg = "Bad credentials.";
        }
        if(logout){
            msg = "You have logged out successfully.";
        }
        if(register){
            msg = "Registered successfully." +
                    "\nLogin please!!";
        }
        model.addAttribute("msg",msg);
        return "login";
    }

    @RequestMapping("/dashboard")
    public String displayDashboard(Authentication auth, Model user, HttpSession httpSession){
        String username = auth.getName();
        Person person;
        if(null != username && username.contains("@")) {
            person = personRepository.findByEmail(username);
        } else{
            person = personRepository.findByMobileNumber(username);
        }
        user.addAttribute("username",person.getName());
        user.addAttribute("authorities",person.getRoles().getRoleName());
        httpSession.setAttribute("loggedInUser",person);
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
