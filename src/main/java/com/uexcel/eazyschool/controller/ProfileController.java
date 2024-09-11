package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.model.Profile;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.service.PersonService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ProfileController {
    private final PersonRepository personRepository;
    private final PersonService personService;

    @Autowired
    public ProfileController(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @RequestMapping(value = "displayProfile", method = RequestMethod.GET)
    public ModelAndView displayProfilePage(HttpSession session,
                                           @RequestParam(value = "isUpdated",required = false) boolean isUpdate
    ) {
        Person person = (Person) session.getAttribute("loggedInUser");
        if (person == null) {
            throw new RuntimeException("Could not load the profile page!");
        }
        Person obj = personRepository.findByEmail(person.getEmail());
        Profile profile = getProfile(obj, person);

        ModelAndView modelAndView = new ModelAndView("profilePage");
       modelAndView.addObject("profile", profile);
       modelAndView.addObject("isUpdated", isUpdate);
       return modelAndView;
    }


    @RequestMapping(value = "updateProfile", method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute("profile")
                                    Profile profile, Errors errors,
                                HttpSession session) {
        if (errors.hasErrors()) {
            return "profilePage";
        }
        boolean isUpdated = personService.updatePersonProfile(profile,session);
        if (isUpdated) {
            return "redirect:/displayProfile?isUpdated=true";
        }
        return "profilePage";
    }

    private static Profile getProfile(Person obj, Person person) {
        Profile profile = new Profile();
        profile.setName(obj.getName());
        profile.setEmail(obj.getEmail());
        profile.setMobileNumber(obj.getMobileNumber());
        if(person.getAddress() != null) {
            profile.setAddress1(obj.getAddress().getAddress1());
            profile.setAddress2(obj.getAddress().getAddress2());
            profile.setCity(obj.getAddress().getCity());
            profile.setState(obj.getAddress().getState());
            profile.setZipCode(obj.getAddress().getZipCode());
        }
        return profile;
    }
}

