package com.uexcel.eazyschool.model;

import com.uexcel.eazyschool.annotation.FieldsValueMatch;
import com.uexcel.eazyschool.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Password do not match!"
        ),
        @FieldsValueMatch(field = "email",
                fieldMatch = "confirmEmail",
                message = "Email address do not match!")
})
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[a-zA-Z]{3,}[A-Za-z ]*")
    private String name;

    @Email
    private String email;

    @Transient  //it indicates to spring to ignore filed for jpa operations
    private String confirmEmail;

    @Pattern(regexp = "(\\+234[7-9]|0[7-9])[01][0-9]{8}",message = "Invalid phone Nigeria number!")
    private String mobileNumber;


    @Size(min = 6, message = "Password should be up to 6 characters!")
    @PasswordValidator
    private String pwd;

    @Transient           //it indicates to spring to ignore filed for jpa operations
    private String confirmPwd;

    @OneToOne( fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName ="id",nullable = false )
    private Roles roles;

    @OneToOne( fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName ="id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = true )
    private SchoolClass schoolClass;


}
