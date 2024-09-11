package com.uexcel.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "class")
@Entity
public class SchoolClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,targetEntity = Person.class)
    private Set<Person> persons;
}
