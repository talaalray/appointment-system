package com.appointment_management.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateServiceRequest {
    public String name;
    public Integer durationMinutes;
    public BigDecimal price;
    public Long providerId;
}
