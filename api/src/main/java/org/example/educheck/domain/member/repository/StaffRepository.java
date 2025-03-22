package org.example.educheck.domain.member.repository;

import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByMemberId(Long memberId);

    Optional<Staff> findByMember(Member member);
}
