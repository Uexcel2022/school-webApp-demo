package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Address;
import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.model.Profile;
import com.uexcel.eazyschool.repository.PersonRepository;
import com.uexcel.eazyschool.repository.RolesRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private  final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository repository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = repository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean savePerson(Person person){
        person.setRoles(rolesRepository.findByRoleName(EazySchoolConstants.STUDENT_ROLE));
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        Person ps =  personRepository.save(person);
        return ps.getId() > 0;
    }

    public boolean updatePersonProfile(Profile profile, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInUser");
        Person toUpdatePerson = personRepository.findByEmail(person.getEmail());
        toUpdatePerson.setName(profile.getName());
        toUpdatePerson.setEmail(profile.getEmail());
        toUpdatePerson.setMobileNumber(profile.getMobileNumber());
        Address address = new Address();
        address.setAddress1(profile.getAddress1());
        address.setAddress2(profile.getAddress2());
        address.setCity(profile.getCity());
        address.setState(profile.getState());
        address.setZipCode(profile.getZipCode());
        toUpdatePerson.setAddress(address);
        return personRepository.save(toUpdatePerson).getAddress().getId() > 0;

    }
}
