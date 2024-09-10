package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    Person findByMobileNumber(String mobileNumber);
}
