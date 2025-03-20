package org.example.educheck.domain.member.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.educheck.domain.member.entity.Member;

@Entity
@Getter
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(100)")
    private Type type;

}
