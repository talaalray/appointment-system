package com.appointment_management.demo.service;

import com.appointment_management.demo.entity.WorkingSchedule;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.repository.ScheduleRepository;
import com.appointment_management.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class WorkingScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public WorkingScheduleService(ScheduleRepository scheduleRepository,
                                  UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    // إنشاء/إضافة دوام ليوم محدد لمقدم خدمة (STAFF)

    public WorkingSchedule addSchedule(Long providerId,
                                       DayOfWeek dayOfWeek,
                                       LocalTime startTime,
                                       LocalTime endTime) {

        if (dayOfWeek == null) throw new RuntimeException("dayOfWeek is required");
        if (startTime == null || endTime == null) throw new RuntimeException("startTime and endTime are required");
        if (!startTime.isBefore(endTime)) throw new RuntimeException("startTime must be before endTime");

        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        WorkingSchedule ws = new WorkingSchedule();
        ws.setProvider(provider);
        ws.setDayOfWeek(dayOfWeek);
        ws.setStartTime(startTime);
        ws.setEndTime(endTime);

        return scheduleRepository.save(ws);
    }

    // تعديل دوام

    public WorkingSchedule updateSchedule(Long scheduleId,
                                          LocalTime startTime,
                                          LocalTime endTime) {

        WorkingSchedule ws = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (startTime != null) ws.setStartTime(startTime);
        if (endTime != null) ws.setEndTime(endTime);

        if (!ws.getStartTime().isBefore(ws.getEndTime())) {
            throw new RuntimeException("startTime must be before endTime");
        }

        return ws;
    }

    @Transactional(readOnly = true)
    public List<WorkingSchedule> getSchedulesForProvider(Long providerId) {
        return scheduleRepository.findByProviderId(providerId);
    }

    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
