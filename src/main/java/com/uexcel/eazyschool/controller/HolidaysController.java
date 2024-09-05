package com.uexcel.eazyschool.controller;

import com.uexcel.eazyschool.model.Holiday;
import com.uexcel.eazyschool.service.HolidaysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

//@Controller
//@EnableAspectJAutoProxy
public class HolidaysController {

    private final HolidaysService holidaysService;

    private static final Logger log = LoggerFactory.getLogger(HolidaysController.class);

    public HolidaysController(HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
    }

    @RequestMapping(value = "holidays", method = RequestMethod.GET)
    public String displayHolidays(Model model){

        List<Holiday> holidays = holidaysService.getAllHolidays();

//        List<Holiday> holidays =
//                Arrays.asList(
//                        new Holiday("Oct 1","Independence Day",Holiday.Type.FEDERAL),
//                        new Holiday("Jun 12 ","Democracy Day",Holiday.Type.FEDERAL),
//                        new Holiday("May 29","Children's Day",Holiday.Type.FESTIVAL),
//                        new Holiday("Jan 1", "New Year's Day", Holiday.Type.FESTIVAL),
//                        new Holiday("Dec 25","Christmas",Holiday.Type.FESTIVAL),
//                        new Holiday("May 1","Labour Day",Holiday.Type.FEDERAL)
//                );

        Holiday.Type[] types = Holiday.Type.values();
        for(Holiday.Type type : types) {
            model.addAttribute(type.toString(), holidays.stream()
                    .filter(el -> el.getType().equals(type)).toList());
        }

        return "holidays";
    }

    @RequestMapping(value = "/saveHoliday", method = RequestMethod.POST)
    public String saveHoliday(@ModelAttribute Holiday holiday){
        holidaysService.saveHoliday(holiday);
        return "redirect:/holidays";
    }

}
