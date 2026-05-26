package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateAppointmentRequest {
    public Long providerId;
    public Long serviceId;
    public LocalDateTime startDateTime;
    public String note;
}
/*
    "startDateTime" : "2025-12-30T13:30",
*/
