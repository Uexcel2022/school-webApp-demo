package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.service.ContactJpaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
                                    Errors errors){
        if(errors.hasErrors()){
            log.info("{}",errors.getAllErrors());
            return new ModelAndView("contact");
        }
       Contact ct =  contactjpaService.saveMessage(contact);

        if(null != ct && ct.getId()<=0){
            throw  new RuntimeException("Message save failed.");
        }
        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping(value = "/displayMessages/page/{currentPage}",method = RequestMethod.GET)
    public ModelAndView getContactMessage(@PathVariable("currentPage") int currentPage,
                                          @RequestParam(value = "sortField",defaultValue = "createdAt") String sortField,
                                          @RequestParam(value = "sortDir",defaultValue = "dsc") String sortDir){
        ModelAndView mav = new ModelAndView("messages");

        String sortDirection = sortDir.equals("asc") ? "desc" : "asc";

        Page<Contact> contactMsgs = contactjpaService
                .findContactMsgWithOpenStatus(currentPage,sortField,sortDirection);

        mav.addObject("currentPage", currentPage);
        mav.addObject("totalPages", contactMsgs.getTotalPages());
        mav.addObject("totalMsgs", contactMsgs.getTotalElements());
        mav.addObject("sortField", sortField);
        mav.addObject("sortDir", sortDirection);
        mav.addObject("reverseSortDir", sortDirection);
        mav.addObject("contactMsgs", contactMsgs.getContent());
        return mav;
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public ModelAndView updateMessage(@RequestParam("id") Long id, @RequestParam("pageNumber")int currentPage,
                                @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir ){
      contactjpaService.updateMessageStatus(id);
      String sortDirection = sortDir.equals("asc") ? "desc" : "asc";
      String url = String.format("redirect:/displayMessages/page/%s?sortField=%s&sortDir=%s",currentPage,sortField,sortDirection);
        return  new ModelAndView(url);
    }

}
