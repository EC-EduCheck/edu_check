package org.example.educheck.domain.staffcourse.repository;

import org.example.educheck.domain.staffcourse.entity.StaffCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StaffCourseRepository extends JpaRepository<StaffCourse, Long> {

    @Query("SELECT sc " +
            "FROM StaffCourse sc " +
            "JOIN sc.staff s " +
            "JOIN sc.course c " +
            "WHERE s.id = :staffId " +
            "AND c.id = :courseId")
    Optional<StaffCourse> findByStaffIdAndCourseId(Long staffId, Long courseId);
}
