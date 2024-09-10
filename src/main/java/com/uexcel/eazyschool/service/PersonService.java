package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository repository;
    private  final RolesRepository rolesRepository;

    @Autowired
    public PersonService(PersonRepository repository, RolesRepository rolesRepository) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
    }

    public boolean savePerson(Person person){
        person.setRoles(rolesRepository.findByRoleName(EazySchoolConstants.STUDENT_ROLE));
        Person ps =  repository.save(person);
        return ps.getId() > 0;
    }
}
