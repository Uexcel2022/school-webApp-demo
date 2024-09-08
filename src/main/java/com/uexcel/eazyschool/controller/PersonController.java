package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/public")
public class PersonController {
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String displayRegistrationPage(Model model) {
        model.addAttribute("person", new Person());
      return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String savePerson(@ModelAttribute Person person) {
        return "redirect:/public/register";
    }
}
