package org.example.educheck.domain.absenceattendance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * isApprove : T 승인 F 반려 null 대기
 */
@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbsenceAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approver_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDate startTime;
    private LocalDate endTime;
    private Character isApprove;
    private LocalDateTime approveDate;
    private String reason;
    private String category;

    @Builder
    public AbsenceAttendance(Staff staff, Course course, Student student, LocalDate startTime, LocalDate endTime, Character isApprove, LocalDateTime approveDate, String reason, String category) {
        this.staff = staff;
        this.course = course;
        this.student = student;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isApprove = isApprove;
        this.approveDate = approveDate;
        this.reason = reason;
        this.category = category;
    }
}
