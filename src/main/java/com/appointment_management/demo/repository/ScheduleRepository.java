package com.appointment_management.demo.repository;

import com.appointment_management.demo.entity.WorkingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<WorkingSchedule, Long> {
  List<WorkingSchedule> findByProviderId(Long providerId);
  Optional<WorkingSchedule> getByProvider_IdAndAndDayOfWeek(Long providerId, DayOfWeek day);

}