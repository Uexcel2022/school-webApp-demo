package com.uexcel.eazyschool.authentication;

import com.uexcel.eazyschool.model.Person;
import com.uexcel.eazyschool.repository.PersonRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PersonRepository personRepository;

    public UsernamePasswordAuthenticationProvider(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if(password == null || username == null) {
            throw new BadCredentialsException("Bad credentials!");
        }

        Person person = null;
        if(username.contains("@")) {
            person = personRepository.findByEmail(username);
        } else{
            person = personRepository.findByMobileNumber(username);
        }

        if(null != person && person.getId() > 0 && password.equals(person.getPwd())) {
            return new UsernamePasswordAuthenticationToken(person.getName(), password,
                    List.of(new GrantedAuthority[]{new SimpleGrantedAuthority(person.getRoles().getRoleName())}));
        }
        throw  new BadCredentialsException("Bad credentials!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
