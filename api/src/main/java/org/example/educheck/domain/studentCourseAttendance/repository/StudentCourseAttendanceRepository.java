package org.example.educheck.domain.studentCourseAttendance.repository;

import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentCourseAttendanceRepository extends JpaRepository<StudentCourseAttendance, Integer> {

    List<StudentCourseAttendance> findByIdStudentIdAndIdCourseId(Long studentId, Long courseId);

    Page<StudentCourseAttendance> findByIdStudentIdAndIdCourseId(Long studentId, Long courseId, Pageable pageable);


}
