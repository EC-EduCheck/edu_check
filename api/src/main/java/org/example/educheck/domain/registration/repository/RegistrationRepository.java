package org.example.educheck.domain.registration.repository;

import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByStudentIdAndStatus(Long studentId, Status status);

    Optional<Registration> findByStudentIdAndCourseId(Long id, Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
