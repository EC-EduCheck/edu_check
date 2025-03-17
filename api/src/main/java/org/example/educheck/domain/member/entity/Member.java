package org.example.educheck.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false)
  private String email;
  private String name;
  private String phoneNumber;
  private LocalDate birthDate;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public Member(String email, String name, String phoneNumber, LocalDate birthDate, String password, Role role) {
    this.email = email;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.birthDate = birthDate;
    this.password = password;
    this.role = role;
  }

}
