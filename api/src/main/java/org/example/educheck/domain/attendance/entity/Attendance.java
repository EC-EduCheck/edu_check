package org.example.educheck.domain.attendance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.entity.BaseTimeEntity;

@Entity
public class Attendance extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  private LocalDateTime checkInTimestamp;
  private LocalDateTime checkOutTimestamp;

  @Enumerated(EnumType.STRING)
  private Status status;

}
