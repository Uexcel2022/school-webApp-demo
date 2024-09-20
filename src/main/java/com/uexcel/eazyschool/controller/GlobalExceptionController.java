package com.uexcel.eazyschool.controller;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@EnableAspectJAutoProxy
@Order(2)
public class GlobalExceptionController {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("errorMsg", e.getMessage());
        return errorPage;
    }
}
