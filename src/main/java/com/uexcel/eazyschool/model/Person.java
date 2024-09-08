package com.uexcel.eazyschool.model;

import lombok.Data;

@Data
public class Person {
    private int id;
    private String name;
    private String email;
    private String confirmEmail;
    private String mobileNumber;
    private String pwd;
    private String confirmPwd;
}
