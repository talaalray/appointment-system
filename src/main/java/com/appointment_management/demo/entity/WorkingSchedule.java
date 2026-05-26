package com.appointment_management.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name="working_schedule")
@Getter @Setter
public class WorkingSchedule {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  private User provider; // staff

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private DayOfWeek dayOfWeek;

  @Column(nullable=false)
  private LocalTime startTime;

  @Column(nullable=false)
  private LocalTime endTime;
}