package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.SchoolClass;
import com.uexcel.eazyschool.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    public AdminController(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    @RequestMapping(value = "displayClasses", method = RequestMethod.GET)
    public ModelAndView displayClasses(){
        List<SchoolClass> classList = schoolClassRepository.findAll();
        ModelAndView mav = new ModelAndView("classes");
        mav.addObject("schoolClass", new SchoolClass());
        mav.addObject("classList", classList);
        return mav;
    }

    @RequestMapping(value = "addNewClass", method = RequestMethod.POST)
    public String saveNewClasses(@ModelAttribute("schoolClass") SchoolClass schoolClass){
        schoolClassRepository.save(schoolClass);
        return "redirect:/admin/displayClasses";
    }

    @RequestMapping(value = "deleteClass", method = RequestMethod.GET)
    public String deleteClass(@RequestParam("id")  Long id) {
          schoolClassRepository.deleteById(id);
          return "redirect:/admin/displayClasses";
    }
}
