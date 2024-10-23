package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.model.EazySchoolProps;
import com.uexcel.eazyschool.repository.ContactJpaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContactJpaService {
    private final ContactJpaRepository contactJpaRepository;
    @Autowired
    private EazySchoolProps eazySchoolProps;

    public ContactJpaService(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;

    }


    public Contact saveMessage(Contact contact) {
        contact.setStatus(EazySchoolConstants.OPEN);
       return contactJpaRepository.save(contact);
    }

    public Page<Contact> findContactMsgWithOpenStatus(int currentPage,String sortField, String sortDirection) {
//        log.error("**************************{}", eazySchoolProps);

        int pageSize = eazySchoolProps.getPageSize() == 0 ?
                Integer.parseInt(eazySchoolProps.getContact().get("pageSize").trim()): eazySchoolProps.getPageSize();

        Pageable pageable = PageRequest.of(currentPage -1,pageSize,
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
