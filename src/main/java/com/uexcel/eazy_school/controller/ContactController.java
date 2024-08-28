package com.uexcel.eazy_school.controller;

import com.uexcel.eazy_school.model.Contact;
import com.uexcel.eazy_school.service.ContactService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model,@RequestParam(required = false) boolean success){
        model.addAttribute("contact", new Contact());
        model.addAttribute("success", success);
        return "contact";
    }

    @RequestMapping( value = "/saveMsg",method = RequestMethod.POST)
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact,
                                    Errors errors){
        if(errors.hasErrors()){
            log.info("{}",errors.getAllErrors());
            return new ModelAndView("contact");
        }

        boolean isSave = contactService.saveMessage(contact);
        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping(value = "/displayMessages",method = RequestMethod.GET)
    public ModelAndView getContactMessage(){
        List<Contact> contactMsgList = contactService.findContactMsgWithOpenStatus();
        return new ModelAndView("messages", "contactMsgList", contactMsgList);
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public String updateMessage(@RequestParam int id,Authentication auth){
      boolean isUpdated = contactService.updateMessageStatus(id,auth.getName());

      if(!isUpdated){
          throw new RuntimeException("We encountered an error... couldn't close the contact message. Please try again.");
      }
        return "redirect:/displayMessages";
    }

}
