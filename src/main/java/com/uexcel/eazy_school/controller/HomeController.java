package com.uexcel.eazy_school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value = {"","logon","/","home"})
    public String displayHomePage(){
        return "home";
    }

}
