package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/public")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String displayRegistrationPage(Model model) {
        model.addAttribute("person", new Person());
      return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String savePerson(@Valid @ModelAttribute Person person, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        boolean isSaved = personService.savePerson(person);
        if (isSaved) {
            return "redirect:/login?register=true";
        }
        return "register";
    }
}
