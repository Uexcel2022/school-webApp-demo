package com.uexcel.eazyschool.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    private String day;
    private String reason;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type{
        FESTIVAL, FEDERAL
    }

}
