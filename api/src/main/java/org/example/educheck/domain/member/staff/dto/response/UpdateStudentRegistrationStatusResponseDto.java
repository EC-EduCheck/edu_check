package org.example.educheck.domain.member.staff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.registration.entity.Status;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UpdateStudentRegistrationStatusResponseDto {

    private Long studentId;
    private Long courseId;
    private String studentName;
    private String courseName;
    private Status status;

    public static UpdateStudentRegistrationStatusResponseDto from(Long studentId, Long courseId, String studentName, String courseName, Status status) {
        return UpdateStudentRegistrationStatusResponseDto.builder()
                .studentId(studentId)
                .courseId(courseId)
                .studentName(studentName)
                .courseName(courseName)
                .status(status)
                .build();
    }

}
