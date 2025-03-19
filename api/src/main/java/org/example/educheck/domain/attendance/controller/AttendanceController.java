package org.example.educheck.domain.attendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.service.AttendanceService;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/checkin")
    public ResponseEntity<ApiResponse<Void>> checkIn(
            @AuthenticationPrincipal Student student,
            @Valid @RequestBody AttendanceCheckinRequestDto requestDto
    ) {
        // student가 null인 경우 처리
        if (student == null) {
            // 테스트용으로 ID 1인 학생 사용
            attendanceService.checkIn(1L, requestDto);
        } else {
            attendanceService.checkIn(student.getId(), requestDto);
        }
        return ResponseEntity.ok(
                ApiResponse.ok
                        (
                                "출석 성공",
                                "SUCCESS",
                                null
                        )
        );
    }
}
