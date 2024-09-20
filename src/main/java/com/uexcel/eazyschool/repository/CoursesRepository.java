package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

 // proves path name for rest data
@Repository
@RepositoryRestResource(path = "courses")
//@RepositoryRestResource(exported = false) //if you don't want the repository exported...
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Courses> findAllByOrderByName();

    List<Courses> findAllByOrderByNameDesc();

}
