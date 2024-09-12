package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {
    boolean existsByName(String name);
}
