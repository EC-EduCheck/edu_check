package org.example.educheck.domain.absenceattendance.controller;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceAttendanceRequestDto;
import org.example.educheck.domain.absenceattendance.dto.response.GetAbsenceAttendancesResponseDto;
import org.example.educheck.domain.absenceattendance.service.AbsenceAttendanceService;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/course/{courseId}/absence-attendances")
public class AbsenceAttendanceController {
    private final AbsenceAttendanceService absenceAttendanceService;


    @PatchMapping("/{absesnceAttendancesId}")
    public ResponseEntity<ApiResponse<Void>> processAbsenceAttendanceService(@PathVariable Long courseId, @PathVariable Long absesnceAttendancesId,
                                                                             @RequestBody ProcessAbsenceAttendanceRequestDto requestDto,
                                                                             @AuthenticationPrincipal Member member) {

        absenceAttendanceService.processAbsenceAttendanceService(courseId, absesnceAttendancesId, requestDto, member);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok("유고 결석 처리 성공", "OK", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<GetAbsenceAttendancesResponseDto>> getAbsenceAttendances(
            @PathVariable Long courseId, @PageableDefault(sort = "startTime",
            direction = Sort.Direction.DESC,
            size = 10)
    Pageable pageable, @AuthenticationPrincipal Member member) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                                "특정 교육 과정 유고 결석 내역 조회 성공",
                                "OK", absenceAttendanceService.getAbsenceAttendances(courseId, pageable, member)
                        )
                );
    }
}
