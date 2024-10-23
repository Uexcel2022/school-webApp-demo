package com.uexcel.eazyschool.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EazySchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> eazyMap = new HashMap<>();
        eazyMap.put("App Name", "Eazy School");
        eazyMap.put("App Version", "1.0.0");
        eazyMap.put("Build Date", "2024-09-21");
        eazyMap.put("App Description", "Eazy School web application for Student and Admin");
        eazyMap.put("Contact-email", "eazyschool@gmail.com");
        eazyMap.put("Contact mobile", "+234000292020");
        builder.withDetail("eazyschool-info",eazyMap );
    }
}
