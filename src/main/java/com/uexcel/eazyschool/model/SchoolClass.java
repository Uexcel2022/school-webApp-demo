package com.uexcel.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@Table(name = "class")
@Entity
public class SchoolClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY, targetEntity = Person.class)
    private Set<Person> persons;
}
