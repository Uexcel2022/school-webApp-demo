package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.service.ContactJpaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@EnableAspectJAutoProxy
public class ContactJpaController {
    private final ContactJpaService contactjpaService;

    @Autowired
    public ContactJpaController(ContactJpaService contactJpaService) {
        this.contactjpaService = contactJpaService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model,@RequestParam(required = false) boolean success){
        model.addAttribute("contact", new Contact());
        model.addAttribute("success", success);
        return "contact";
    }

    @RequestMapping( value = "/saveMsg",method = RequestMethod.POST)
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact,
                                    Errors errors, Authentication authentication){
        if(errors.hasErrors()){
            log.info("{}",errors.getAllErrors());
            return new ModelAndView("contact");
        }

        contactjpaService.saveMessage(contact,authentication);
        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping(value = "/displayMessages",method = RequestMethod.GET)
    public ModelAndView getContactMessage(){
        List<Contact> contactMsgList = contactjpaService.findContactMsgWithOpenStatus();
        return new ModelAndView("messages", "contactMsgList", contactMsgList);
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public String updateMessage(@RequestParam Long id,Authentication auth){
      contactjpaService.updateMessageStatus(id,auth.getName());
        return "redirect:/displayMessages";
    }

}
