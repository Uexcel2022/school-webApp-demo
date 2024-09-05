package com.uexcel.eazyschool.repository;

import com.uexcel.eazyschool.model.Holiday;
import com.uexcel.eazyschool.rowmapper.HolidaysRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@Repository
public class HolidaysRepository {
    private final JdbcTemplate jdbcTemplate;

    public HolidaysRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean saveHoliday(Holiday holiday) {
       String sql = "INSERT INTO HOLIDAYS (DAY SEASON,TYPE) VALUES (?,?,?)";
       int rs = jdbcTemplate
               .update(sql, holiday.getDay(), holiday.getReason(), holiday.getType());
       return rs > 1;
    }

    public List<Holiday> getAllHolidays() {

//        use wen the table columns' names are same as the been fields' names
//        var rowMapper = BeanPropertyRowMapper.newInstance(Holiday.class);

        return jdbcTemplate.query("SELECT * FROM HOLIDAYS", new HolidaysRowMapper());

    }
}
