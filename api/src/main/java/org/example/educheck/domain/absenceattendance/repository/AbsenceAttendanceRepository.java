package org.example.educheck.domain.absenceattendance.repository;

import org.example.educheck.domain.absenceattendance.dto.response.MyAbsenceAttendanceResponseDto;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbsenceAttendanceRepository extends JpaRepository<AbsenceAttendance, Long> {
    Page<AbsenceAttendance> findByCourseId(Long courseId, Pageable pageable);

    @Query(value = "SELECT new org.example.educheck.domain.absenceattendance.dto.response.MyAbsenceAttendanceResponseDto( " +
            "a.id, a.startTime, a.endTime, a.isApprove, a.category) " +
            "FROM AbsenceAttendance a " +
            "WHERE a.course.id = :courseId AND a.student.id = :studentId " +
            "AND a.deletionRequestedAt IS NULL " +
            "ORDER BY a.createdAt DESC",
            countQuery = "SELECT COUNT(a) FROM AbsenceAttendance a " +
                    "WHERE a.course.id = :courseId AND a.student.id = :studentId")
    Page<MyAbsenceAttendanceResponseDto> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId, Pageable pageable);

}
