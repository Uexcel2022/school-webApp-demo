package com.uexcel.eazyschool.controller;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAspectJAutoProxy
public class HomeController {
    @RequestMapping(value = {"","logon","/","home"})
    public String displayHomePage(){
        return "home";
    }

}
