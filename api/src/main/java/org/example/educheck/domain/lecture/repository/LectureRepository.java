package org.example.educheck.domain.lecture.repository;

import org.example.educheck.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByCourseIdAndDate(Long courseId, LocalDateTime date);
}
