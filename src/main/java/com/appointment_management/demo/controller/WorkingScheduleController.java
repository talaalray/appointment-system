package com.appointment_management.demo.controller;

import com.appointment_management.demo.dto.AddScheduleRequest;
import com.appointment_management.demo.dto.UpdateScheduleRequest;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.entity.WorkingSchedule;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.service.WorkingScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static com.appointment_management.demo.service.UserService.getUser;

@RestController
@RequestMapping("/schedules")
public class WorkingScheduleController {

    private final WorkingScheduleService workingScheduleService;

    public WorkingScheduleController(WorkingScheduleService workingScheduleService) {
        this.workingScheduleService = workingScheduleService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkingSchedule> add(@RequestBody AddScheduleRequest req) {

        WorkingSchedule ws = workingScheduleService.addSchedule(
                req.providerId, req.dayOfWeek, req.startTime, req.endTime
        );
        return ResponseEntity.ok(ws);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkingSchedule> update(@PathVariable Long id,
                                                  @RequestBody UpdateScheduleRequest req) {
        WorkingSchedule ws = workingScheduleService.updateSchedule(id, req.startTime, req.endTime);
        return ResponseEntity.ok(ws);
    }

    @GetMapping("/provider/{providerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkingSchedule>> byProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(workingScheduleService.getSchedulesForProvider(providerId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workingScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}