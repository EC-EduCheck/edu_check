package org.example.educheck.domain.attendance.repository;

import org.example.educheck.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.student = :studentId AND a.lecture = :lectureId")
    Attendance findByLectureIdStudentId(Long studentId, Long lectureId);

    Optional<Attendance> findByStudentIdAndCheckInTimestampBetween(
            Long studentId,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    @Query("SELECT a FROM Attendance a WHERE a.student = :studentId AND DATE(a.checkInTimestamp) = :day")
    Optional<Attendance> findByStudentIdAndCheckInExist(Long studentId, LocalDate day);
}
