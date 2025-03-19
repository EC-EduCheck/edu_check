package org.example.educheck.domain.member.student.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.member.entity.Member;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, NORMAL, WITHDRAW

    private char courseParticipationStatus;

    @Builder
    public Student(Member member, Status status, char courseParticipationStatus) {
        this.member = member;
        this.status = status;
        this.courseParticipationStatus = courseParticipationStatus;
    }
}
