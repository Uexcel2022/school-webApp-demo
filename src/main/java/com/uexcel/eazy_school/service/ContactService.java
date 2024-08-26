package com.uexcel.eazy_school.service;

import com.uexcel.eazy_school.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContactService {
    public boolean saveMessage(Contact contact) {
        log.info("User message: {}", contact);
        return true;
    }
}
