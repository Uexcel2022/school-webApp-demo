package com.uexcel.eazyschool.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
//@PropertySource(value = "classpath:some.properties")
@ConfigurationProperties(prefix = "eazy.school")
@Component
@Data
@Validated
public class EazySchoolProps {
    @Max(value = 25, message = "Page size should not be more than 25!")
    @Min(value = 5,message = "Page size should not be less than 5!")
    private int pageSize;
    private Map<String,String> contact;
    List<String> branches;
}
