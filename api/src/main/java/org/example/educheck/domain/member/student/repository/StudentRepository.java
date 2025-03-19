package org.example.educheck.domain.member.student.repository;

import org.example.educheck.domain.member.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
