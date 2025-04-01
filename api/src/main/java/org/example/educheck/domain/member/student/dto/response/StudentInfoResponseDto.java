package org.example.educheck.domain.member.student.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.registration.entity.RegistrationStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StudentInfoResponseDto {

    private Long memberId;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhoneNumber;
    private RegistrationStatus registrationStatus;

}
