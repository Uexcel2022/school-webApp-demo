package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Holiday;
import com.uexcel.eazyschool.repository.HolidayJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@EnableAspectJAutoProxy
public class JpaHolidaysController {

    private final HolidayJpaRepository holidayJpaRepository
;
    private static final Logger log = LoggerFactory.getLogger(JpaHolidaysController.class);
    @Autowired
    public JpaHolidaysController(HolidayJpaRepository holidayJpaRepository) {

        this.holidayJpaRepository = holidayJpaRepository;
    }

    @RequestMapping(value = "holidays", method = RequestMethod.GET)
    public String displayHolidays(Model model){

        List<Holiday> holidays = holidayJpaRepository.findAll();


        Holiday.Type[] types = Holiday.Type.values();
        for(Holiday.Type type : types) {
            model.addAttribute(type.toString(), holidays.stream()
                    .filter(el -> el.getType().equals(type)).toList());
        }

        return "holidays";
    }

    @RequestMapping(value = "/saveHoliday", method = RequestMethod.POST)
    public String saveHoliday(@ModelAttribute Holiday holiday){
        holidayJpaRepository.save(holiday);
        return "redirect:/holidays";
    }

}
