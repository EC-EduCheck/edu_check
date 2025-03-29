package org.example.educheck.domain.registration.repository;

import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByStudentIdAndRegistrationStatus(Long studentId, RegistrationStatus registrationStatus);

    Optional<Registration> findByStudentIdAndCourseId(Long id, Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Registration> findByCourseId(Long courseId);
}
