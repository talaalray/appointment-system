package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateServiceRequest {
    public String name;
    public int durationMinutes;
    public BigDecimal price;
    public Long providerId;
}