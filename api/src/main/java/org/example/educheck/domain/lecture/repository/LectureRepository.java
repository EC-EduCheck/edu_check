package org.example.educheck.domain.lecture.repository;

import org.example.educheck.domain.lecture.Lecture;
import org.example.educheck.domain.member.dto.response.AttendanceRateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findByCourseIdAndDate(Long courseId, LocalDateTime date);

    @Query("SELECT l FROM Lecture l WHERE l.course.id = :courseId AND l.date BETWEEN :startDate AND :endDate")
    Optional<Lecture> findByCourseIdAndDateBetween(
            @Param("courseId") Long courseId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    List<Lecture> findAllByCourseId(Long courseId);

    @Query(value = """
            SELECT
                member_id AS memberId,
                student_id AS studentId,
                progressingCount,
                COUNT(attendanceId) AS totalAttendanceCount,
                COUNT(CASE WHEN attendanceStatus = 'ATTENDANCE' THEN 1 END) AS presentCount,
                COUNT(CASE WHEN attendanceStatus = 'LATE' THEN 1 END) AS lateCount,
                COUNT(CASE WHEN attendanceStatus = 'EARLY_LEAVE' THEN 1 END) AS earlyLeaveCount,
                ROUND((COUNT(CASE WHEN attendanceStatus = 'LATE' THEN 1 END) +
                    COUNT(CASE WHEN attendanceStatus = 'EARLY_LEAVE' THEN 1 END)) / 3) AS adjustedAbsenceCount,
                progressingCount - COUNT(attendanceId) AS absenceCount,
                progressingCount - COUNT(attendanceId) +
                    ROUND((COUNT(CASE WHEN attendanceStatus = 'LATE' THEN 1 END) +
                           COUNT(CASE WHEN attendanceStatus = 'EARLY_LEAVE' THEN 1 END)) / 3) AS totalAbsenceCount,
                COUNT(attendanceId) -
                    ROUND((COUNT(CASE WHEN attendanceStatus = 'LATE' THEN 1 END) +
                           COUNT(CASE WHEN attendanceStatus = 'EARLY_LEAVE' THEN 1 END)) / 3) AS recognizedAttendanceCount,
                (COUNT(attendanceId) -
                    ROUND((COUNT(CASE WHEN attendanceStatus = 'LATE' THEN 1 END) +
                           COUNT(CASE WHEN attendanceStatus = 'EARLY_LEAVE' THEN 1 END)) / 3)) / progressingCount * 100 AS todayAttendanceRate,
                count(lecture_id) / totalLectureCount * 100 AS overallAttendanceRate,
                progressingCount / totalLectureCount * 100 AS courseProgressRate
            FROM lecture_attendance
            WHERE course_id = :courseId AND member_id = :memberId
            group by student_id
            """, nativeQuery = true)
    AttendanceRateProjection findAttendanceRateByCourse(@Param("courseId") Long courseId,
                                                        @Param("memberId") Long memberId);

}
