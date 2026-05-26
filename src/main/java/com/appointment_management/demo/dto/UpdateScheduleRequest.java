package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateScheduleRequest {
    public LocalTime startTime;
    public LocalTime endTime;
}
