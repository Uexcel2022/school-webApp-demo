package com.uexcel.eazyschool.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Profile {
    @Pattern(regexp = "[a-zA-Z]{3,}[A-Za-z ]*")
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "(\\+234[7-9]|0[7-9])[01][0-9]{8}",message = "Invalid phone Nigeria number!")
    private String mobileNumber;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;

}
