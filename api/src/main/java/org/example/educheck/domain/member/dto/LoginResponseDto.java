package org.example.educheck.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime lastLoginDate;
    private Long campusId;
    private Long courseId;
    private String courseName;
}
