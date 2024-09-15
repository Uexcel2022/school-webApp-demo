package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactJpaRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findContactByStatus(String open, Pageable pageable);
}
