package com.appointment_management.demo.controller;

import com.appointment_management.demo.dto.AddHolidayRequest;
import com.appointment_management.demo.entity.Holiday;
import com.appointment_management.demo.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Holiday> add(@RequestBody AddHolidayRequest req) {
        Holiday h = holidayService.addHoliday(req.providerId, req.date, req.reason);
        return ResponseEntity.ok(h);
    }

    @GetMapping("/provider/{providerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Holiday>> byProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(holidayService.getHolidaysForProvider(providerId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
}