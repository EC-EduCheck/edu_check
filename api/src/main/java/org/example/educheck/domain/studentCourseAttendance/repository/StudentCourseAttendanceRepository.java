package org.example.educheck.domain.studentCourseAttendance.repository;

import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRateStatisticsProjection;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceStatsProjection;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StudentCourseAttendanceRepository extends JpaRepository<StudentCourseAttendance, Integer> {


    @Query("SELECT sca FROM StudentCourseAttendance sca " +
            "WHERE sca.id.courseId = :courseId " +
            "AND DATE( sca.lectureDate) = DATE(NOW())")
    List<StudentCourseAttendance> findByCourseIdAndLectureDateIsToday(@Param("courseId") Long courseId);

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

    @Query(value = """
                    SELECT
                        student_id,
                        member_id,
                        member_name,
                        course_id,
                        start_date,
                        end_date,
                        COUNT(lecture_date) AS progressCount,
                        COUNT(CASE WHEN attendance_status = 'ATTENDANCE' THEN 1 END) AS attendance_count,
                        COUNT(CASE WHEN attendance_status = 'LATE' THEN 1 END) AS late_count,
                        COUNT(CASE WHEN attendance_status = 'EARLY_LEAVE' THEN 1 END) AS early_leave_count,
                        COUNT(CASE WHEN attendance_status = 'ABSENT' THEN 1 END) AS absent_count,
                        FLOOR((COUNT(CASE WHEN attendance_status = 'LATE' THEN 1 END)
                            + COUNT(CASE WHEN attendance_status = 'EARLY_LEAVE' THEN 1 END)
                            ) / 3)
                            + COUNT(CASE WHEN attendance_status = 'ABSENT' THEN 1 END) AS accumulated_absence
                    FROM student_course_attendance
                    WHERE lecture_date <= CURDATE()
                    AND member_id = :memberId
                    AND course_id = :courseId
                    GROUP BY student_id, member_id, member_name, course_id
            """, nativeQuery = true)
    AttendanceStatsProjection findAttendanceStatsByStudentId(@Param("memberId") Long studentId, @Param("courseId") Long courseId);


    @Query(value = """
            WITH attendance_counts AS (
                SELECT
                    student_id, member_id, member_name, course_id, course_name,
                    SUM(IF(attendance_status = 'ATTENDANCE' AND lecture_date <= CURDATE(), 1, 0)) AS attendance_count,
                    SUM(IF(attendance_status = 'LATE' AND lecture_date <= CURDATE(), 1, 0)) AS late_count,
                    SUM(IF(attendance_status = 'EARLY_LEAVE' AND lecture_date <= CURDATE(), 1, 0)) AS early_leave_count,
                    SUM(IF(attendance_status = 'ABSENT' AND lecture_date <= CURDATE(), 1, 0)) AS absent_count,
                    SUM(IF(lecture_date <= CURDATE(),1,0)) AS lectures_count_until_today,
                    COUNT(lecture_id) AS total_lecture,
                    SUM(IF(lecture_date = CURDATE(),1,0)) AS has_lecture_today
                FROM student_course_attendance a
                WHERE a.member_id = :memberId AND course_id = :courseId
                GROUP BY student_id, member_id, member_name, course_id, course_name
            )
            SELECT
                student_id, member_id, member_name, course_id, course_name,
                IF(lectures_count_until_today > 0,
                   ROUND((lectures_count_until_today - absent_count - ((late_count + early_leave_count) / 3)) / lectures_count_until_today * 100, 2), NULL) AS attendance_rate_until_today,
                IF(total_lecture > 0,
                   ROUND((lectures_count_until_today - absent_count - ((late_count + early_leave_count) / 3)) / total_lecture * 100, 2), NULL) AS total_attendance_rate,
                IF(total_lecture > 0,
                   ROUND(lectures_count_until_today / total_lecture * 100, 2), NULL) AS course_progress_rate
            FROM attendance_counts
            """, nativeQuery = true)
    AttendanceRateStatisticsProjection findAttendanceRateStatistics(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

}
