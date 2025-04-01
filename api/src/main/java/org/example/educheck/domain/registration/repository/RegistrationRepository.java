package org.example.educheck.domain.registration.repository;

import org.example.educheck.domain.member.student.dto.response.StudentInfoResponseDto;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByStudentIdAndRegistrationStatus(Long studentId, RegistrationStatus registrationStatus);

    Optional<Registration> findByStudentIdAndCourseId(Long id, Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    
    @Query("SELECT new org.example.educheck.domain.member.student.dto.response.StudentInfoResponseDto(" +
            "m.id, s.id, m.name, m.email, m.phoneNumber, r.registrationStatus) " +
            "FROM Registration r " +
            "JOIN r.student s " +
            "JOIN s.member m " +
            "WHERE r.course.id = :courseId")
    List<StudentInfoResponseDto> findByCourseIdWithStudentAndMember(@Param("courseId") Long courseId);
}
