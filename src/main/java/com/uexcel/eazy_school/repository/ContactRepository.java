package com.uexcel.eazy_school.repository;

import com.uexcel.eazy_school.model.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {
    private  final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int saveContactMsg(Contact contact) {
        String sql = "INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS" +
                ",CREATED_AT,CREATED_BY) values(?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,contact.getName(),
                contact.getMobileNum(), contact.getEmail(),
                contact.getSubject(), contact.getMessage(),
                contact.getStatus(), contact.getCreatedAt(),
                contact.getCreatedBy()
        );
    }
}
