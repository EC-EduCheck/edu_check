package org.example.educheck.domain.attendance.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.lecture.entity.Lecture;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @Column(columnDefinition = "VARCHAR(50)")
    private AttendanceStatus attendanceStatus;

    @Builder
    public Attendance(Student student, Lecture lecture, LocalDateTime checkInTimestamp) {
        this.student = student;
        this.lecture = lecture;
        this.checkInTimestamp = checkInTimestamp;
    }

    public Attendance updateStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        return this;
    }

}
