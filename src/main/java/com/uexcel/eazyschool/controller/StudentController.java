package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
    @GetMapping("displayCourses")
    public ModelAndView displayEnrolledCourses(HttpSession session){
        ModelAndView mav = new ModelAndView("courses_enrolled");
        Person person = (Person)session.getAttribute("loggedInUser");
        mav.addObject("person", person);
        return mav;

    }
}
