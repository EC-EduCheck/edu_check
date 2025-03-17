package org.example.educheck.domain.member.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.entity.BaseTimeEntity;


@Entity
public class Student extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  private Status status; // PENDING, NORMAL, WITHDRAW

  private char courseParticipationStatus;
}
