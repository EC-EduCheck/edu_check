package org.example.educheck.domain.member.student.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.registration.entity.Registration;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Status status; // PENDING, NORMAL, WITHDRAW

    private char courseParticipationStatus;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    @Builder
    public Student(Member member, Status status, char courseParticipationStatus, List<Registration> registrations) {
        this.member = member;
        this.status = status;
        this.courseParticipationStatus = courseParticipationStatus;
        this.registrations = registrations;
    }
}
