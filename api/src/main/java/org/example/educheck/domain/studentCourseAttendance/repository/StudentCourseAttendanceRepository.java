package org.example.educheck.domain.studentCourseAttendance.repository;

import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StudentCourseAttendanceRepository extends JpaRepository<StudentCourseAttendance, Integer> {

    List<StudentCourseAttendance> findByIdStudentIdAndIdCourseId(Long studentId, Long courseId);

    Page<StudentCourseAttendance> findByIdStudentIdAndIdCourseId(Long studentId, Long courseId, Pageable pageable);

    @Query("SELECT sca FROM StudentCourseAttendance sca " +
            "WHERE sca.id.studentId = :studentId " +
            "AND sca.id.courseId = :courseId " +
            "AND (:year IS NULL OR FUNCTION('YEAR', sca.lectureDate) = :year) " +
            "AND (:month IS NULL OR FUNCTION('MONTH', sca.lectureDate) = :month)")
    Page<StudentCourseAttendance> findByStudentAndCourse(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId,
            @Param("year") Integer year,
            @Param("month") Integer month,
            Pageable pageable);

}
