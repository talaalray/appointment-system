package com.appointment_management.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name="services")
@Getter @Setter
public class ServiceEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String name;

  @Column(nullable=false)
  private int durationMinutes;

  @Column(nullable=false)
  private BigDecimal price;

  // مقدم الخدمة Staff
  @ManyToOne(optional=false)
  private User provider; // role STAFF
}
