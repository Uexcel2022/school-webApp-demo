package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    Person findByMobileNumber(String mobileNumber);

    List<Person> findBySchoolClass(SchoolClass schoolClass);
}
