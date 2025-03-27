package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendanceattachmentfile.dto.response.GetAttachmentFileReposeDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GetMyAbsenceAttendancesResponseDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private String category;
    private String reason;
    private String approveDate;
    private Character isApprove;
    private List<GetAttachmentFileReposeDto> files;

}
