package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class AddScheduleRequest {
    public DayOfWeek dayOfWeek;
    public LocalTime startTime;
    public LocalTime endTime;
    public Long providerId;
}

/*
    "startTime": "08:00",
    "endTime" : "22:00"
*/
