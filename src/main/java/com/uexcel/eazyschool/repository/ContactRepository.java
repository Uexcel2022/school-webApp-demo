package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.rowmapper.ContactRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//@Repository
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

    public List<Contact> findContactMsgWithOpenStatus(String open) {
        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql, new ContactRowMapper(), open);
    }

    public int updateMessageStatus(int id, String status, String updatedBy) {
        LocalDateTime time = LocalDateTime.now();
        String sql = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_AT= ?, UPDATED_BY = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, status, time, updatedBy, id);
    }
}
