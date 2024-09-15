package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Courses> findAllByOrderByName();

    List<Courses> findAllByOrderByNameDesc();

}
