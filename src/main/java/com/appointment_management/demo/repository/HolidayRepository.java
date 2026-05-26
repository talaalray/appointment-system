package com.appointment_management.demo.repository;

import com.appointment_management.demo.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
  boolean existsByProviderIdAndDate(Long providerId, LocalDate date);

  List<Holiday> findByProviderId(Long providerId);
}