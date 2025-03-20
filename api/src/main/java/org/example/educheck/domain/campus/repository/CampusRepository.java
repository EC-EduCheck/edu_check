package org.example.educheck.domain.campus.repository;

import org.example.educheck.domain.campus.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
}
