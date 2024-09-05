package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactJpaRepository extends JpaRepository<Contact, Long> {
    List<Contact> findContactByStatus(String open);
}
