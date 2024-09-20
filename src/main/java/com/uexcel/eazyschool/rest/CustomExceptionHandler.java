package com.uexcel.eazyschool.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionHandler extends RuntimeException {
    private int statusCode;
    private String msg;
}
