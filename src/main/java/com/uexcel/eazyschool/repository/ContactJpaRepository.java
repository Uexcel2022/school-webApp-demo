package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactJpaRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findContactByStatus(@Param("status")String status, Pageable pageable);

    @Query(value = "Select c FROM  Contact c  where c.status=:status")
    List<Contact> findContactByStatusWithQuery(@Param("status")String status);

    List<Contact> findByOrderByNameDesc();

    List<Contact> findByOrderByNameAsc();
}
