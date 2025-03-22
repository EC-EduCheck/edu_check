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

    @Query("SELECT l FROM Lecture l WHERE l.course.id = :courseId AND l.date = :today")
    Optional<Lecture> findByCourseToday(
            @Param("courseId") Long courseId,
            @Param("today") LocalDateTime today
    );

    List<Lecture> findAllByCourseId(Long courseId);

    @Query(value = """
             SELECT
                s.id AS studentId,
                m.id AS memberId,
                COUNT(a.id) AS totalAttendance,
                COUNT(CASE WHEN a.status = 'ATTENDANCE' THEN 1 END) AS attendanceCount,
                COUNT(CASE WHEN a.status = 'LATE' THEN 1 END) AS lateCount,
                COUNT(CASE WHEN a.status = 'EARLY_LEAVE' THEN 1 END) AS earlyLeaveCount,
                (COUNT(l.id) - COUNT(a.id)) AS absenceCount,
                FLOOR(
                        (COUNT(CASE WHEN a.status = 'LATE' THEN 1 END)
                            + COUNT(CASE WHEN a.status = 'EARLY_LEAVE' THEN 1 END)) / 3
                ) AS adjustedAbsenceCount,
                (
                    (COUNT(l.id) - COUNT(a.id)) +
                    FLOOR(
                            (COUNT(CASE WHEN a.status = 'LATE' THEN 1 END)
                                + COUNT(CASE WHEN a.status = 'EARLY_LEAVE' THEN 1 END)) / 3
                    )
                ) AS totalAbsenceCount,
                ROUND(
                    (
                        COUNT(l.id) -
                        (
                            (COUNT(l.id) - COUNT(a.id)) +
                            FLOOR(
                                    (COUNT(CASE WHEN a.status = 'LATE' THEN 1 END) +
                                     COUNT(CASE WHEN a.status = 'EARLY_LEAVE' THEN 1 END)) / 3
                            )
                        )
                    ) / COUNT(l.id) * 100
                )    AS attendanceRate
            FROM lecture l
                     LEFT JOIN attendance a
                               ON DATE(l.date) = DATE(a.check_in_timestamp)
                     LEFT JOIN student s
                               ON a.student_id = s.id
                     LEFT JOIN member m
                               ON s.member_id = m.id
             WHERE l.course_id = :courseId
             AND s.member_id = :memberId
            """, nativeQuery = true)
    AttendanceRateProjection findAttendanceRateByCourse(@Param("courseId") Long courseId,
                                                        @Param("memberId") Long memberId);

}
