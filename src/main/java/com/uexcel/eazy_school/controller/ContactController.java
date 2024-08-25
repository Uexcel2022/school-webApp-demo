package com.uexcel.eazy_school.controller;

import com.uexcel.eazy_school.model.Contact;
import com.uexcel.eazy_school.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Controller
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @RequestMapping( value = "/saveMsg",method = RequestMethod.POST)
    public ModelAndView saveMessage(@Valid @ModelAttribute Contact contact,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("{}",bindingResult.getAllErrors());
            return new ModelAndView("contact");
        }
        boolean isSave = contactService.saveMessage(contact);
        return new ModelAndView("redirect:/contact");
    }
}
