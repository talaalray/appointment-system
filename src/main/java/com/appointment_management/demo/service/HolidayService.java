package com.appointment_management.demo.service;

import com.appointment_management.demo.entity.Holiday;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.repository.HolidayRepository;
import com.appointment_management.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final UserRepository userRepository;

    public HolidayService(HolidayRepository holidayRepository,
                          UserRepository userRepository) {
        this.holidayRepository = holidayRepository;
        this.userRepository = userRepository;
    }

    public Holiday addHoliday(Long providerId, LocalDate date, String reason) {
        if (date == null) throw new RuntimeException("date is required");

        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (holidayRepository.existsByProviderIdAndDate(providerId, date)) {
            throw new RuntimeException("Holiday already exists for this date");
        }

        Holiday h = new Holiday();
        h.setProvider(provider);
        h.setDate(date);
        h.setReason(reason);

        return holidayRepository.save(h);
    }

    @Transactional(readOnly = true)
    public List<Holiday> getHolidaysForProvider(Long providerId) {

        return holidayRepository.findByProviderId(providerId);
    }

    public void deleteHoliday(Long holidayId) {
        if (!holidayRepository.existsById(holidayId)) {
            throw new RuntimeException("Holiday not found");
        }
        holidayRepository.deleteById(holidayId);
    }
}
