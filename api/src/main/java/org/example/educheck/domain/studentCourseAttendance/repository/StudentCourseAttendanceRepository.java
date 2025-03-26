package org.example.educheck.domain.studentCourseAttendance.repository;

import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseAttendanceRepository extends JpaRepository<StudentCourseAttendanceRepository, StudentCourseAttendanceId> {

    findByStudentIdAndCourseId(StudentCourseAttendanceId studentCourseAttendanceId, Course course);
}
