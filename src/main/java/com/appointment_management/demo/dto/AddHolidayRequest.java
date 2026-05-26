package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddHolidayRequest {
    public Long providerId;
    public LocalDate date;
    public String reason;
}
