package org.example.educheck.domain.member.student.repository;

import org.example.educheck.domain.member.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
        Optional<Student> findByMemberId(Long memberId);
}
