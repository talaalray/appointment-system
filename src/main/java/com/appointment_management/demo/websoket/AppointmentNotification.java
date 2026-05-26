package com.appointment_management.demo.websoket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentNotification{
        private Long appointmentId;
        private String status;
        private String message;
}