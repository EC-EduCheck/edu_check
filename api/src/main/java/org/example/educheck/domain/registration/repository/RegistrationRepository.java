package org.example.educheck.domain.registration.repository;

import org.example.educheck.domain.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
