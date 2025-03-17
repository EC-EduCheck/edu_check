package org.example.educheck.domain.attendance.entity;

import jakarta.persistence.*;
import org.example.educheck.domain.lecture.Lecture;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    private LocalDateTime checkInTimestamp;
    private LocalDateTime checkOutTimestamp;

    @Enumerated(EnumType.STRING)
    private Status status;

}
