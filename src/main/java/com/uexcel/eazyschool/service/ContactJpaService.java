package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.repository.ContactJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public Contact saveMessage(Contact contact) {
        contact.setStatus(EazySchoolConstants.OPEN);
       return contactJpaRepository.save(contact);
    }

    public Page<Contact> findContactMsgWithOpenStatus(int currentPage,String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(currentPage -1,5,
                sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return contactJpaRepository.findContactByStatus(EazySchoolConstants.OPEN,pageable);
    }

    public void updateMessageStatus(Long id) {
        Contact toUpdate = contactJpaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Not message found"));
        toUpdate.setStatus(EazySchoolConstants.CLOSE);
        contactJpaRepository.save(toUpdate);
    }
}
