package org.example.educheck.domain.attendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.response.StudentAttendanceListResponseDto;
import org.example.educheck.domain.attendance.service.StaffAttendanceService;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Staff가 출석 관리하는 컨트롤러
@Slf4j
@RestController
@RequiredArgsConstructor
public class StaffAttendanceController {

    private final StaffAttendanceService staffAttendanceService;

    @GetMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<StudentAttendanceListResponseDto>> getStudentAttendances(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 세부 출결 현황 조회 성공",
                "OK",
                staffAttendanceService.getStudentAttendancList(member, courseId, studentId)
        ));
    }
}


