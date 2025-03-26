package org.example.educheck.domain.studentCourseAttendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Immutable
public class StudentCourseAttendance {

    @EmbeddedId
    private StudentCourseAttendanceId id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "registration_status")
    private String registrationStatus;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "lecture_session")
    private Long lectureSession;

    @Column(name = "lecture_date")
    private LocalDate lectureDate;

    @Column(name = "lecture_title")
    private String lectureTitle;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    @Column(name = "check_in_timestamp")
    private LocalDateTime checkInTimestamp;

    @Column(name = "check_out_timestamp")
    private LocalDateTime checkOutTimestamp;

    @Override
    public String toString() {
        return "StudentCourseAttendance{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", registrationStatus='" + registrationStatus + '\'' +
                ", courseName='" + courseName + '\'' +
                ", lectureSession='" + lectureSession + '\'' +
                ", lectureDate='" + lectureDate + '\'' +
                ", lectureTitle='" + lectureTitle + '\'' +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                ", checkInTimestamp='" + checkInTimestamp + '\'' +
                ", checkOutTimestamp='" + checkOutTimestamp + '\'' +
                '}';
    }
}
