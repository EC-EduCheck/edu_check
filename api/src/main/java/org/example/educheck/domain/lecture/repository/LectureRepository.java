package org.example.educheck.domain.lecture.repository;

import org.example.educheck.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByCourseIdAndDate(Long courseId, LocalDateTime date);
    @Query("SELECT l FROM Lecture l WHERE l.course.id = :courseId AND l.date BETWEEN :startDate AND :endDate")
    Optional<Lecture> findByCourseIdAndDateBetween(
            @Param("courseId") Long courseId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
