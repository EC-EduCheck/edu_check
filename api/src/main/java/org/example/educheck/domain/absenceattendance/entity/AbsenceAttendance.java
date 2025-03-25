package org.example.educheck.domain.absenceattendance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;

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
public class AbsenceAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approver_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "absenceAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AbsenceAttendanceAttachmentFile> absenceAttendanceAttachmentFiles = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Character isApprove;
    private LocalDateTime approveDate;
    private String reason;

    private LocalDateTime deletionRequestedAt;
}
