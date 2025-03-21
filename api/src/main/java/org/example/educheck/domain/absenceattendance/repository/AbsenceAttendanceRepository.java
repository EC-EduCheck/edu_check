package org.example.educheck.domain.absenceattendance.repository;

import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceAttendanceRepository extends JpaRepository<AbsenceAttendance, Long> {
}
