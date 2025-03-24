package org.example.educheck.domain.member.repository;

import org.example.educheck.domain.member.dto.LoginResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
                select new org.example.educheck.domain.member.dto.LoginResponseDto(
                     m.email, m.name, m.phoneNumber, m.birthDate, m.lastLoginDate, c.id, cr.id, cr.name
                )
                from Member m
                LEFT JOIN Student s ON s.member.id = m.id
                LEFT JOIN Registration r ON r.student.id = s.id
                LEFT JOIN Course cr ON r.course.id = cr.id AND NOW() BETWEEN cr.startDate AND cr.endDate
                LEFT JOIN Campus c ON cr.campus.id = c.id
                WHERE m.id = :memberId
            """)
    Optional<LoginResponseDto> studentLoginResponseDtoByMemberId(@Param("memberId") Long memberId);

    @Query("""
            select new org.example.educheck.domain.member.dto.LoginResponseDto(
                 m.email, m.name, m.phoneNumber, m.birthDate, m.lastLoginDate, c.id, cr.id, cr.name
            )
            from Member m
            LEFT JOIN Staff st ON st.member.id = m.id
            LEFT JOIN StaffCourse sc ON sc.staff.id = st.id
            LEFT JOIN Course cr ON sc.course.id = cr.id AND NOW() BETWEEN cr.startDate AND cr.endDate
            LEFT JOIN Campus c ON cr.campus.id = c.id
            WHERE m.id = :memberId
            """)
    Optional<LoginResponseDto> staffLoginResponseDtoByMemberId(@Param("memberId") Long memberId);


}