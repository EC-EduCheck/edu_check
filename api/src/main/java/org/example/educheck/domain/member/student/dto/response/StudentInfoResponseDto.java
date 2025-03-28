package org.example.educheck.domain.member.student.dto.response;
import org.example.educheck.domain.registration.entity.Status;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StudentInfoResponseDto {

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhoneNumber;
    private Status registrationStatus;

    public static StudentInfoResponseDto from(Long studentId, String studentName, String studentEmail, String studentPhoneNumber, Status registrationStatus) {
        return StudentInfoResponseDto.builder()
                .studentId(studentId)
                .studentName(studentName)
                .studentEmail(studentEmail)
                .studentPhoneNumber(studentPhoneNumber)
                .registrationStatus(registrationStatus)
                .build();
    }
}
