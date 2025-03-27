package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GetAbsenceAttendanceResponseDto {

    //조회 신청한 사람 Id( member, staff 둘 다 가능)
    private Long memberId;
    private String memberName;
    private AbsenceAttendanceResponseDto absenceAttendance;

    public static GetAbsenceAttendanceResponseDto from(Long memberId, String memberName, AbsenceAttendanceResponseDto absenceAttendance) {
        return GetAbsenceAttendanceResponseDto.builder()
                .memberId(memberId)
                .memberName(memberName)
                .absenceAttendance(absenceAttendance)
                .build();
    }

}
