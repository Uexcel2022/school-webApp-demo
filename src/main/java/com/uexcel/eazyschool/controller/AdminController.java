package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.model.PersonClass;
import com.uexcel.eazyschool.model.SchoolClass;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final SchoolClassRepository schoolClassRepository;
    private final PersonRepository personRepository;

    @Autowired
    public AdminController(SchoolClassRepository schoolClassRepository, PersonRepository personRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "displayClasses", method = RequestMethod.GET)
    public ModelAndView displayClasses(HttpSession session,
                                       @RequestParam(value = "errorMessage",required = false) String errorMessage){

        if(session.getAttribute("classId") != null){
            session.removeAttribute("classId");
        }
        List<SchoolClass> classList = schoolClassRepository.findAll();
        ModelAndView mav = new ModelAndView("classes");
        mav.addObject("schoolClass", new SchoolClass());
        mav.addObject("classList", classList);
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    @RequestMapping(value = "addNewClass", method = RequestMethod.POST)
    public ModelAndView saveNewClasses(@ModelAttribute("schoolClass") SchoolClass schoolClass){
        ModelAndView mav = new ModelAndView();
        if(schoolClassRepository.existsByName(schoolClass.getName())){
            String msg = schoolClass.getName()+ " exist in the record!";
            mav.setViewName("redirect:/admin/displayClasses?errorMessage="+msg);
            return mav;
        }
        schoolClassRepository.save(schoolClass);
        mav.setViewName("redirect:/admin/displayClasses");
        return mav;
    }

    @RequestMapping(value = "deleteClass", method = RequestMethod.GET)
    public ModelAndView  deleteClass(@RequestParam("classId")  Long classId) {
        ModelAndView mav = new ModelAndView();

          SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow();
          List<Person> person = personRepository.findBySchoolClass(schoolClass);
          schoolClassRepository.save(schoolClass);

          for (Person p : person) {
              p.setSchoolClass(null);
              personRepository.save(p);
          }
          schoolClassRepository.deleteById(classId);

          mav.setViewName("redirect:/admin/displayClasses");
          return mav;
    }

    @RequestMapping(value = "displayStudents",method = RequestMethod.GET)
    public ModelAndView displayStudent(@RequestParam(value = "classId",required = false) Long id,
                                       HttpSession session,@RequestParam(value = "errorMessage",required = false)
                                           String errorMessage){
        if(session.getAttribute("classId") == null){
            session.setAttribute("classId",id);
        }
        long classId = (long) session.getAttribute("classId");

        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow();
        List<Person> person = personRepository.findBySchoolClass(schoolClass);
        PersonClass personClass = new PersonClass();
        ModelAndView mav = new ModelAndView("students");
        mav.addObject("schoolClass", schoolClass);
        mav.addObject("persons", person);
        mav.addObject("personClass",personClass);
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    @RequestMapping(value = "add-student-to-class",method = RequestMethod.POST)
    public ModelAndView addStudentToClass(@ModelAttribute("personClass") PersonClass personClass, Model model,
                                    HttpSession session){
        ModelAndView mav = new ModelAndView();
        long classId = (long) session.getAttribute("classId");

        Person person = personRepository.findByEmail(personClass.getEmail());

        if(person == null){
            String msg = "No student found with email: " +personClass.getEmail();
            mav.setViewName("redirect:/admin/displayStudents?errorMessage="+msg);
            return mav;
        }

        if(person.getSchoolClass() != null){
            String msg = "This student is in "+person.getSchoolClass().getName();
            mav.setViewName("redirect:/admin/displayStudents?errorMessage="+msg);
            return mav;
        }

        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow();
        person.setSchoolClass(schoolClass);
        personRepository.save(person);
        mav.setViewName("redirect:/admin/displayStudents");
        return mav;
    }

    @RequestMapping(value = "deleteStudent",method = RequestMethod.GET)
    public ModelAndView deleteStudentFromClass(@RequestParam("personId") long personId) {
        ModelAndView mav = new ModelAndView();
        Person person = personRepository.findById(personId).orElseThrow();
        person.setSchoolClass(null);
        personRepository.save(person);
        mav.setViewName("redirect:/admin/displayStudents");
        return mav;
    }

}
