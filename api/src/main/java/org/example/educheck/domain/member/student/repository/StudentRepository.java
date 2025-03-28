package org.example.educheck.domain.member.student.repository;

import org.example.educheck.domain.member.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
        Optional<Student> findByMemberId(Long memberId);

        @Query("SELECT s " +
                "FROM Student s " +
                "LEFT JOIN Registration r ON s.id = r.student.id " +
                "LEFT JOIN Course c ON r.course.id = c.id " +
                "WHERE c.id = :courseId")
        List<Student> findAllByCourseId(Long courseId);
}
