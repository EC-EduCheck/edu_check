package org.example.educheck.domain.studentCourseAttendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class StudentCourseAttendance {

    @EmbeddedId
    private StudentCourseAttendanceId id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "registration_status")
    private String registrationStatus;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "lecture_id")
    private String lectureId;

    @Column(name = "lecture_session")
    private String lectureSession;

    @Column(name = "lecture_date")
    private String lectureDate;

    @Column(name = "lecture_title")
    private String lectureTitle;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    @Column(name = "check_in_timestamp")
    private String checkInTimestamp;

    @Column(name = "check_out_timestamp")
    private String checkOutTimestamp;
}
