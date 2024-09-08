package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidayJpaRepository extends CrudRepository<Holiday,Integer> {
}
