package com.uexcel.eazy_school.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Setter @Getter @ToString
public class Contact {
    @Pattern(regexp = "[A-Za-z]{2,}[a-zA-z ]*",message = "Name should be at least 2 alphabet characters")
    private String name;
    @Pattern(regexp = "(\\+234[7-9]|0[7-9])[01][0-9]{8}",message = "Invalid phone Nigeria number")
    private String mobileNum;
    @Email
    private String email;
    @Pattern(regexp = "[a-zA-Z ,?.'$%0-9-]+",message = "Subject should be at least 10 or at most 50 characters long")
    @Length(min=5,max=50)
    private String subject;
    @Pattern(regexp = "[a-zA-Z ,?.'$%0-9-]+",message = "Message should be at least 10 or at most 100 characters long")
    @Length(min=10,max=100)
    private String message;
}
