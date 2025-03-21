package org.example.educheck.domain.absenceattendance.controller;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceRequestDto;
import org.example.educheck.domain.absenceattendance.service.AbsenceService;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/course/{courseId}/absence-attendances")
public class AbsenceAttendanceController {
    private final AbsenceService absenceService;


    @PutMapping("/{absesnceAttendancesId}")
    public T processAbsenceService(@PathVariable Long courseId, @PathVariable Long absesnceAttendancesId,
                                   @RequestBody ProcessAbsenceRequestDto requestDto,
                                   @AuthenticationPrincipal Member member) {

        return absenceService.processAbsenceService(courseId, absesnceAttendancesId, requestDto, member);
    }
}
