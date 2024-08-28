package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessage(Contact contact) {
        contact.setStatus(EazySchoolConstants.OPEN);
        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int rs = contactRepository.saveContactMsg(contact);
        return rs > 0;
    }

    public List<Contact> findContactMsgWithOpenStatus() {
        return contactRepository
                .findContactMsgWithOpenStatus(EazySchoolConstants.OPEN);
    }

    public boolean updateMessageStatus(int id, String updateBy) {
       int rs =  contactRepository
               .updateMessageStatus(id,EazySchoolConstants.CLOSE,updateBy);
        return rs > 0;
    }
}
