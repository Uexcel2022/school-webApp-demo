package com.uexcel.eazyschool.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
//@RequestScope
//@SessionScope
//@ApplicationScope
@Entity
@Table(name="contact_msg")
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "[A-Za-z]{2,}[a-zA-z ]{2,30}",
            message = "Name should be at least 2 or at most 30 alphabet characters long.")
    @JsonProperty("person_name")
    private String name;
    @Pattern(regexp = "(\\+234[7-9]|0[7-9])[01][0-9]{8}",message = "Invalid Nigeria phone number.")
    @JsonProperty(value = "mobile_number")
    private String mobileNum;
    @Email
    private String email;
    @Pattern(regexp = "[a-zA-Z ,?.'$%0-9-]{5,50}",
            message = "Subject should be at least 5 or at most 50 characters long.")
    private String subject;
    @Pattern(regexp = "[a-zA-Z ,?.'$%0-9-]{10,100}",
            message = "Message should be at least 10 or at most 100 characters long.")
    private String message;

    private String status;

}
