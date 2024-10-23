package com.uexcel.eazyschool.controller;


import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
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

    @Value("${page.size}")
    private int defaultPageSize;

    @Value("${success.message}")
    private String message;

    @Autowired
    private Environment environment;

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
    public String displayDashboard(Authentication auth, Model user, HttpSession session){

        removeSessionAttributes(session,"classId");
        removeSessionAttributes(session,"courseId");

        String username = auth.getName();
        Person person;

        if(null != username && username.contains("@")) {
            person = personRepository.findByEmail(username);
        } else{
            person = personRepository.findByMobileNumber(username);
        }
        String name = person.getName().toUpperCase().charAt(0)
                + person.getName().toUpperCase().substring(1).toLowerCase();
        person.setName(name);
        user.addAttribute("username",name);
        user.addAttribute("authorities",person.getRoles().getRoleName());
        user.addAttribute("studentClass",person.getSchoolClass());
        session.setAttribute("loggedInUser",person);
        logging();
        return "dashboard";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication auth){
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }


    private void removeSessionAttributes(HttpSession session,String name){
        if(session.getAttribute(name) != null) {
            session.removeAttribute(name);
        }
    }

    private void logging(){
        log.info("info message from dashboard page");
        log.debug("debug message from dashboard page");
        log.error("Error message from dashboard page");
        log.warn("warning message from dashboard page");
        log.trace("trace message from dashboard page");

        log.error("Default page size: {}",defaultPageSize);
        log.error("success message: {}",message);

        log.error("Environment Default page size: {}", environment.getProperty("page.size"));
        log.error("Environment success message: {}",environment.getProperty("success.message"));
        log.error("Java environment variable: {}",environment.getProperty("JAVA_HOME"));


    }
}
