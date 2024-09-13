package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Courses;
import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.model.PersonClass;
import com.uexcel.eazyschool.model.SchoolClass;
import com.uexcel.eazyschool.repository.CoursesRepository;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final SchoolClassRepository schoolClassRepository;
    private final PersonRepository personRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public AdminController(SchoolClassRepository schoolClassRepository, PersonRepository personRepository,
                           CoursesRepository coursesRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping(value = "displayClasses", method = RequestMethod.GET)
    public ModelAndView displayClasses(HttpSession session,
                                       @RequestParam(value = "errorMessage",required = false) String errorMessage){

        removeSessionAttributes(session, "classId");

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

          SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow();
          List<Person> person = personRepository.findBySchoolClass(schoolClass);
          schoolClassRepository.save(schoolClass);

          for (Person p : person) {
              p.setSchoolClass(null);
              personRepository.save(p);
          }
          schoolClassRepository.deleteById(classId);

          return new ModelAndView("redirect:/admin/displayClasses");

    }

//    ---------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "displayStudents",method = RequestMethod.GET)
    public ModelAndView displayStudent(@RequestParam(value = "classId",required = false) Long id,
                                       HttpSession session,@RequestParam(value = "errorMessage",required = false)
                                           String errorMessage){

        setSessionAttributes(session, "classId",id);

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

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(()->new RuntimeException("Something went wrong. Please try again!"));
        person.setSchoolClass(schoolClass);
        personRepository.save(person);
        schoolClass.getPersons().add(person);
        person.setSchoolClass(schoolClass);
        mav.setViewName("redirect:/admin/displayStudents");
        return mav;
    }

    @RequestMapping(value = "deleteStudent",method = RequestMethod.GET)
    public ModelAndView deleteStudentFromClass(@RequestParam("personId") long personId) {
        Person person = personRepository.findById(personId).orElseThrow(
                ()->new RuntimeException("Something went wrong. Please try again!"));
        person.setSchoolClass(null);
        personRepository.save(person);
        return new ModelAndView("redirect:/admin/displayStudents");

    }

//    ---------------------------------------------------------------------------------------------

    @GetMapping("displayCourses")
    public ModelAndView displayCourses(
            @RequestParam(value = "errorMessage",required = false) String errorMessage, HttpSession session){

        removeSessionAttributes(session, "courseId");

        List<Courses> coursesList = coursesRepository.findAll();
        ModelAndView mav = new ModelAndView("courses_secure");
        mav.addObject("course", new Courses());
        mav.addObject("coursesList", coursesList);
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    @PostMapping("addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course") Courses course){
        ModelAndView mav = new ModelAndView();
        if(coursesRepository.existsByNameIgnoreCase(course.getName())){
            String msg = "The Course: "+ course.getName()+" exist in the record!";
            mav.setViewName("redirect:/admin/displayCourses?errorMessage="+msg);
            return mav;
        };
        coursesRepository.save(course);
        mav.setViewName("redirect:/admin/displayCourses");
        return mav;
    }

// -------------------------------------------------------------------------------------------------------

    @GetMapping(value = "viewStudents")
    public ModelAndView addStudentToCourse(@RequestParam(value = "courseId",required = false) Long id,
                                       HttpSession session,@RequestParam(value = "errorMessage",required = false)
                                       String errorMessage){

        setSessionAttributes(session, "courseId",id);

        long courseId = (long) session.getAttribute("courseId");

        Courses course = coursesRepository.findById(courseId).orElseThrow();

        ModelAndView mav = new ModelAndView("course_students");
        mav.addObject("courses", course);
        mav.addObject("person",new Person());
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    @PostMapping(value = "addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person newPerson,
                                          HttpSession session){
        ModelAndView mav = new ModelAndView();

        long courseId = (long) session.getAttribute("courseId");

        Person person = personRepository.findByEmail(newPerson.getEmail());
        Courses course = coursesRepository.findById(courseId).orElseThrow();
        if(person == null){
            String msg = "No student found with email: " +newPerson.getEmail();
            mav.setViewName("redirect:/admin/viewStudents?errorMessage="+msg);
            return mav;
        }

        if(course.getPerson().contains(person)){
            String msg = "This student already enroll in the course: "+course.getName();
            mav.setViewName("redirect:/admin/viewStudents?errorMessage="+msg);
            return mav;
        }

        person.getCourses().add(course);
        course.getPerson().add(person);
        coursesRepository.save(course);
        personRepository.save(person);
        mav.setViewName("redirect:/admin/viewStudents");
        return mav;
    }

    @GetMapping(value = "deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam("personId") long personId) {
        Person person = personRepository.findById(personId).orElseThrow(
                ()->new RuntimeException("Something went wrong. Please try again!"));
        person.setCourses(null);
        personRepository.save(person);
        return new ModelAndView("redirect:/admin/viewStudents");

    }


    private void setSessionAttributes(HttpSession session,String name, Long id){
        if(session.getAttribute(name) == null){
            session.setAttribute(name, id);
        }
    }

    private void removeSessionAttributes(HttpSession session,String name){
        if(session.getAttribute(name) != null) {
            session.removeAttribute(name);
        }
    }



}
