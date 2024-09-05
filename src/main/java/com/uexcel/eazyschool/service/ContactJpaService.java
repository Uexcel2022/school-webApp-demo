package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.repository.ContactJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class ContactJpaService {
    private final ContactJpaRepository contactJpaRepository;

    public ContactJpaService(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;
    }


    public void saveMessage(Contact contact, Authentication auth) {
        if (auth != null) {
            contact.setCreatedBy(auth.getName());
        } else {
            contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        }
        contact.setStatus(EazySchoolConstants.OPEN);
        contact.setCreatedAt(LocalDateTime.now());
        contactJpaRepository.save(contact);
    }

    public List<Contact> findContactMsgWithOpenStatus() {
        return contactJpaRepository.findContactByStatus(EazySchoolConstants.OPEN);
    }

    public void updateMessageStatus(Long id, String updateBy) {
        Contact toUpdate = contactJpaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Not message found"));
        toUpdate.setStatus(EazySchoolConstants.CLOSE);
        toUpdate.setCreatedBy(updateBy);
        toUpdate.setUpdatedAt(LocalDateTime.now());
        contactJpaRepository.save(toUpdate);
    }
}
