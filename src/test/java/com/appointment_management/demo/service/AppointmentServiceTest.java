package com.appointment_management.demo.service;

import com.appointment_management.demo.entity.Appointment;
import com.appointment_management.demo.entity.ServiceEntity;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.repository.AppointmentRepository;
import com.appointment_management.demo.repository.HolidayRepository;
import com.appointment_management.demo.repository.ScheduleRepository;
import com.appointment_management.demo.repository.ServiceRepository;
import com.appointment_management.demo.websoket.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock private AppointmentRepository appointmentRepository;
    @Mock private ServiceRepository serviceRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private HolidayRepository holidayRepository;
    @Mock private NotificationService notificationService;

    @InjectMocks
    private AppointmentService appointmentService;

    private User customer;
    private ServiceEntity service;
    private LocalDateTime futureTime;

    @BeforeEach
    void setUp() {
        customer = new User();
        customer.setId(1L);
        customer.setRole(Role.CUSTOMER);

        service = new ServiceEntity();
        service.setId(10L);
        service.setDurationMinutes(30);

        User provider = new User();
        provider.setId(99L);
        service.setProvider(provider);

        futureTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
    }

    @Test
    void createAppointment_ShouldThrow_WhenStartTimeIsInThePast() {
        LocalDateTime pastTime = LocalDateTime.now().minusHours(1);
        assertThrows(RuntimeException.class, () ->
            appointmentService.createAppointment(customer, 10L, pastTime, "note")
        );
    }

    @Test
    void createAppointment_ShouldThrow_WhenProviderIsOnHoliday() {
        when(serviceRepository.findById(10L)).thenReturn(Optional.of(service));
        when(holidayRepository.existsByProviderIdAndDate(any(), any())).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
            appointmentService.createAppointment(customer, 10L, futureTime, "note")
        );
    }
}
