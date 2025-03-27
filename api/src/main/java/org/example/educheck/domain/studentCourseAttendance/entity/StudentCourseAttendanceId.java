package org.example.educheck.domain.studentCourseAttendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StudentCourseAttendanceId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseAttendanceId that = (StudentCourseAttendanceId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId) && Objects.equals(lectureId, that.lectureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, lectureId);
    }
}
