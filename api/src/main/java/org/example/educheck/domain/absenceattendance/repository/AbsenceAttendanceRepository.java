package org.example.educheck.domain.absenceattendance.repository;

import org.example.educheck.domain.absenceattendance.dto.response.AbsenceAttendanceResponseDto;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceAttendanceRepository extends JpaRepository<AbsenceAttendance, Long> {
    Page<AbsenceAttendance> findByCourseId(Long courseId, Pageable pageable);


    AbsenceAttendanceResponseDto findDtoById(Long absenceAttendancesId);
}
