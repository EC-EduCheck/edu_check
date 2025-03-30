package org.example.educheck.domain.studentCourseAttendance.repository;

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

}
