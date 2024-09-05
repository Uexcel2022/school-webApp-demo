package com.uexcel.eazyschool.service;

import com.uexcel.eazyschool.constants.EazySchoolConstants;
import com.uexcel.eazyschool.model.Holiday;
import com.uexcel.eazyschool.repository.HolidaysRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

//@Component
public class HolidaysService {
    private final HolidaysRepository holidaysRepository;

    public HolidaysService(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    public boolean saveHoliday(Holiday holiday) {
        holiday.setCreatedAt(LocalDateTime.now());
        holiday.setCreatedBy(EazySchoolConstants.ANONYMOUS);
      return holidaysRepository.saveHoliday(holiday);
    }

    public List<Holiday> getAllHolidays() {
        return holidaysRepository.getAllHolidays();
    }
}
