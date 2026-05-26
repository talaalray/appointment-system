package com.appointment_management.demo.service;

import com.appointment_management.demo.dto.TimeRange;
import com.appointment_management.demo.entity.Appointment;
import com.appointment_management.demo.entity.ServiceEntity;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.entity.WorkingSchedule;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEntityService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final HolidayRepository holidayRepository;

    public ServiceEntityService(ServiceRepository serviceRepository,
                                UserRepository userRepository,
                                AppointmentRepository appointmentRepository,
                                ScheduleRepository scheduleRepository,
                                HolidayRepository holidayRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.scheduleRepository = scheduleRepository;
        this.holidayRepository = holidayRepository;
    }

    @Transactional(readOnly = true)
    public Page<ServiceEntity> getAll(Integer page, Integer size) {
        return serviceRepository.findAll(PageRequest.of(page, size));
    }

    public ServiceEntity createService(String name,
                                       int durationMinutes,
                                       BigDecimal price,
                                       Long providerId) {

        if (name == null || name.isBlank()) throw new RuntimeException("Service name is required");
        if (durationMinutes <= 0) throw new RuntimeException("durationMinutes must be > 0");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("price must be > 0");

        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (!provider.getRole().equals(Role.STAFF)) throw new RuntimeException(" wrong user try again");

        ServiceEntity s = new ServiceEntity();
        s.setName(name);
        s.setDurationMinutes(durationMinutes);
        s.setPrice(price);
        s.setProvider(provider);

        return serviceRepository.save(s);
    }

    public ServiceEntity updateService(Long serviceId,
                                       String name,
                                       Integer durationMinutes,
                                       BigDecimal price,
                                       Long providerId) {

        ServiceEntity s = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (name != null && !name.isBlank()) s.setName(name);
        if (durationMinutes != null) {
            if (durationMinutes <= 0) throw new RuntimeException("durationMinutes must be > 0");
            s.setDurationMinutes(durationMinutes);
        }
        if (price != null) {
            if (price.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("price must be > 0");
            s.setPrice(price);
        }
        if (providerId != null) {
            User user = userRepository.findById(providerId).orElse(null);
            if (user != null && user.getRole().equals(Role.STAFF)){
                s.setProvider(user);
            }
        }
        return s;
    }

    public ServiceEntity updatePrice(Long serviceId, BigDecimal newPrice) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setPrice(newPrice);
        return service;
    }

    @Transactional(readOnly = true)
    public ServiceEntity getById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    @Transactional(readOnly = true)
    public List<ServiceEntity> getByProvider(Long providerId) {
        return serviceRepository.findByProviderId(providerId);
    }

    public void delete(Long serviceId) {
        if (!serviceRepository.existsById(serviceId)) {
            throw new RuntimeException("Service not found");
        }
        serviceRepository.deleteById(serviceId);
    }

    public List<LocalTime> getAvailableTimes(
            Long serviceId,
            LocalDate date
    ) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("service is not found"));
        int duration = service.getDurationMinutes();
        Long providerId = service.getProvider().getId();
        WorkingSchedule workingSchedule = scheduleRepository.getByProvider_IdAndAndDayOfWeek(providerId, date.getDayOfWeek()).orElse(null);
        if (workingSchedule == null)return new ArrayList<>();
        if (holidayRepository.existsByProviderIdAndDate(providerId, date)) return new ArrayList<>();
        int stepMinutes = service.getDurationMinutes() + 5;
        boolean isToday = date.isEqual(LocalDate.now());

        LocalDateTime dayStart = LocalDateTime.of(date, workingSchedule.getStartTime());
        LocalDateTime dayEnd   = LocalDateTime.of(date, workingSchedule.getEndTime());

        List<Appointment> booked = appointmentRepository
                .findByService_ProviderIdAndStartDateTimeBetween(providerId, dayStart, dayEnd);


        List<TimeRange> bookedRanges = booked.stream()
                .map(a -> new TimeRange(
                        a.getStartDateTime(),
                        a.getEndDateTime()
                ))
                .toList();

        List<LocalTime> result = new ArrayList<>();

        for (LocalDateTime t = dayStart;
             !t.plusMinutes(duration).isAfter(dayEnd);
             t = t.plusMinutes(stepMinutes)) {

            LocalDateTime tEnd = t.plusMinutes(duration);

            LocalDateTime finalT = t;
            boolean overlaps = bookedRanges.stream().anyMatch(r ->
                    finalT.isBefore(r.getEnd()) && tEnd.isAfter(r.getStart())
            );

            if (!overlaps) result.add(t.toLocalTime());
        }

        if (isToday){
            result = result.stream().filter(x -> x.isAfter(LocalTime.now())).collect(Collectors.toList());
        }
        return result;
    }
}