package org.example.educheck.domain.member.staff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.member.student.dto.response.StudentInfoResponseDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetStudentsResponseDto {

    private Long courseId;
    private String courseName;
    private List<StudentInfoResponseDto> students;

    public static GetStudentsResponseDto from(Long courseId, String courseName, List<StudentInfoResponseDto> students) {
        return GetStudentsResponseDto.builder()
                .courseId(courseId)
                .courseName(courseName)
                .students(students)
                .build();
    }


}
