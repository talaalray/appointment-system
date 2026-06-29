package com.appointment_management.demo.controller;

import com.appointment_management.demo.dto.CreateAppointmentRequest;
import com.appointment_management.demo.dto.ReasonRequest;
import com.appointment_management.demo.entity.Appointment;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appointment_management.demo.service.UserService.getUser;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //  حجز موعد (PENDING)
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Appointment> create(@RequestBody CreateAppointmentRequest req) {
        User user = getUser();
        Appointment a = appointmentService.createAppointment(
                user,
                req.serviceId,
                req.startDateTime,
                req.note
        );
        return ResponseEntity.ok(a);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Appointment> update(@RequestBody CreateAppointmentRequest req, @PathVariable Long id) {
        User user = getUser();
        Appointment a = appointmentService.updateAppointment(
                id,
                req.startDateTime
        );
        return ResponseEntity.ok(a);
    }


    //  قبول
    @GetMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Appointment> approve(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.approveAppointment(id));
    }

    //  رفض
    @GetMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Appointment> reject(@PathVariable Long id,
                                              @RequestBody(required = false) ReasonRequest req) {
        String reason = (req == null) ? null : req.reason;
        return ResponseEntity.ok(appointmentService.rejectAppointment(id, reason));
    }

    //  إلغاء
    @GetMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id,
                                              @RequestBody(required = false) ReasonRequest req) {
        String reason = (req == null) ? null : req.reason;
        return ResponseEntity.ok(appointmentService.cancelAppointment(id, reason));
    }

    //  إنهاء
    @GetMapping("/{id}/finish")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Appointment> finish(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.finishAppointment(id));
    }

    //  مواعيد الزبون
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Appointment>> byCustomer() {
        User user = getUser();
        return ResponseEntity.ok(appointmentService.getForCustomer(user.getId()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
    //  مواعيد مقدم الخدمة


    @GetMapping("/provider")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<List<Appointment>> byProvider() {
        User user = getUser();
        return ResponseEntity.ok(appointmentService.getAppointmentForProvider(user.getId()));
    }
}


