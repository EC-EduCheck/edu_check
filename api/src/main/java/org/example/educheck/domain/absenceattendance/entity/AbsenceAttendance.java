package org.example.educheck.domain.absenceattendance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.entity.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * isApprove : T 승인 F 반려 null 대기
 */
@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbsenceAttendance extends BaseTimeEntity {

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
    private LocalDateTime approveDateTime;
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private AttendanceStatus category;

    @OneToMany(mappedBy = "absenceAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AbsenceAttendanceAttachmentFile> absenceAttendanceAttachmentFiles = new ArrayList<>();

    private LocalDateTime deletionRequestedAt;

    @Builder
    public AbsenceAttendance(Staff staff, Course course, Student student, LocalDate startTime, LocalDate endTime, Character isApprove, LocalDateTime approveDateTime, String reason, AttendanceStatus category) {
        this.staff = staff;
        this.course = course;
        this.student = student;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isApprove = isApprove;
        this.approveDateTime = approveDateTime;
        this.reason = reason;
        this.category = category;
    }

    public void markDeletionRequested() {
        this.deletionRequestedAt = LocalDateTime.now();
    }

    public List<AbsenceAttendanceAttachmentFile> getActiveFiles() {
        return absenceAttendanceAttachmentFiles.stream()
                .filter(file -> file.getDeletionRequestedAt() == null)
                .toList();
    }


}
