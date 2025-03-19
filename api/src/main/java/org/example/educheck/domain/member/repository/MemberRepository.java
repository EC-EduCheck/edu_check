package org.example.educheck.domain.member.repository;

import org.example.educheck.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
