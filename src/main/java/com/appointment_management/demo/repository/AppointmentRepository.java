package com.appointment_management.demo.repository;

import com.appointment_management.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  @Query("""
        select count(a) > 0 from Appointment a
        where a.service.provider.id = :providerId
          and a.status in (com.appointment_management.demo.enums.AppointmentStatus.PENDING,
                           com.appointment_management.demo.enums.AppointmentStatus.APPROVED)
          and a.startDateTime < :end
          and a.endDateTime > :start
    """)
  boolean existsOverlap(@Param("providerId") Long providerId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

  @Query("""
        select count(a) > 0 from Appointment a
        where a.service.provider.id = :providerId
          and a.id <> :appointmentId
          and a.status in (com.appointment_management.demo.enums.AppointmentStatus.PENDING,
                           com.appointment_management.demo.enums.AppointmentStatus.APPROVED)
          and a.startDateTime < :end
          and a.endDateTime > :start
    """)
  boolean existsOverlapExcludingId(@Param("providerId") Long providerId,
                                   @Param("appointmentId") Long appointmentId,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

  List<Appointment> findByCustomerId(Long customerId);
  List<Appointment> findByService_ProviderId(Long providerId);

    List<Appointment> findByService_ProviderIdAndStartDateTimeBetween(Long providerId, LocalDateTime dayStart, LocalDateTime dayEnd);
}