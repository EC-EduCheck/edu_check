package org.example.educheck.domain.attendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.dto.request.AttendanceUpdateRequestDto;
import org.example.educheck.domain.attendance.dto.response.AttendanceListResponseDto;
import org.example.educheck.domain.attendance.dto.response.AttendanceStatusResponseDto;
import org.example.educheck.domain.attendance.dto.response.StudentAttendanceListResponseDto;
import org.example.educheck.domain.attendance.entity.Status;
import org.example.educheck.domain.attendance.service.AttendanceService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
        Status attendanceStatus = attendanceService.checkIn(user, requestDto);

        AttendanceStatusResponseDto responseDto = new AttendanceStatusResponseDto(attendanceStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        "출석 성공",
                        "OK",
                        responseDto
                ));
    }

    // 수강생 금일 출결 현황 조회
    @GetMapping("/courses/{courseId}/attendances/today")
    public ResponseEntity<ApiResponse<AttendanceListResponseDto>> getTodayAttendances(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserDetails user
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                "금일 출석 현황 조회 성공",
                "OK",
                attendanceService.getTodayAttendances(courseId, user)
        ));
    }

    // 수강생 세부 출결 현황 조회
    @GetMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<StudentAttendanceListResponseDto>> getStudentAttendances(
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @AuthenticationPrincipal UserDetails user
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 세부 출결 현황 조회 성공",
                "OK",
                attendanceService.getStudentAttendances(courseId, studentId, user)
        ));
    }

    // 수강생 출결 상태 수정
    @PatchMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<Object>> updateStudentAttendance(
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @RequestBody AttendanceUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails user
    ) {
        attendanceService.updateStudentAttendance(courseId, studentId, requestDto, user);
        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 출결 수정 성공",
                "OK",
                null
        ));
    }

    @PatchMapping("/checkout")
    public ResponseEntity<ApiResponse<AttendanceStatusResponseDto>> checkOut(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody AttendanceCheckinRequestDto requestDto
    ) {
        Status attendanceStatus = attendanceService.checkOut(user, requestDto);

        AttendanceStatusResponseDto responseDto = new AttendanceStatusResponseDto(attendanceStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                "퇴실 성공",
                "OK",
                responseDto
        ));
    }
}
