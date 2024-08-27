package com.uexcel.eazy_school.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("msg", e.getMessage());
        return errorPage;
    }
}
