package com.uexcel.eazyschool.validations;

import com.uexcel.eazyschool.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value,ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);

        if (fieldValue != null) {
//       commented because we add spring.jpa.properties.javax.persistence.validation.mode=none to disable jpa from doing doing validation
//            if (value.toString().contains("$2a")) {
//                return true;
//            }
            return fieldValue.equals(fieldMatchValue);

        } else {
            return fieldMatchValue == null;
        }
    }
}
