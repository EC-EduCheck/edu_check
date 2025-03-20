package org.example.educheck.domain.attendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.dto.response.AttendanceStatusResponseDto;
import org.example.educheck.domain.attendance.entity.Status;
import org.example.educheck.domain.attendance.service.AttendanceService;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<ApiResponse<AttendanceStatusResponseDto>> checkIn(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody AttendanceCheckinRequestDto requestDto
    ) {
        Status attendanceStatus;
        // student가 null인 경우 처리
        if (user == null) {
            // 테스트용으로 ID 인 학생 사용
            attendanceStatus = attendanceService.checkIn(3L, requestDto);
        } else {
            String email = user.getUsername();
            attendanceStatus = attendanceService.checkInByEmail(email, requestDto);
        }

        AttendanceStatusResponseDto responseDto = new AttendanceStatusResponseDto(attendanceStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("출석 성공", "OK", responseDto));
    }
}
