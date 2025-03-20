package org.example.educheck.domain.staffcourse.repository;

import org.example.educheck.domain.staffcourse.entity.StaffCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffCourseRepository extends JpaRepository<StaffCourse, Long> {

    Optional<StaffCourse> findByStaffIdAndCourseId(Long staffId, Long courseId);
}
