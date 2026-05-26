package com.appointment_management.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="holidays")
@Getter
@Setter
public class Holiday {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  private User provider; // staff (أو null إذا عطلة عامة)

  @Column(nullable=false)
  private LocalDate date;

  private String reason;
}
