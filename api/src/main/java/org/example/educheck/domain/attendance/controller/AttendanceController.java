package org.example.educheck.domain.attendance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.dto.request.AttendanceUpdateRequestDto;
import org.example.educheck.domain.attendance.dto.response.AttendanceStatusResponseDto;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;
import org.example.educheck.domain.attendance.service.AttendanceService;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttendanceController {
    private final AttendanceService attendanceService;


    @PostMapping("/checkin")
    public ResponseEntity<ApiResponse<AttendanceStatusResponseDto>> checkIn(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody AttendanceCheckinRequestDto requestDto
    ) {
        AttendanceStatus attendanceStatus = attendanceService.checkIn(member, requestDto);

        AttendanceStatusResponseDto responseDto = new AttendanceStatusResponseDto(attendanceStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        "출석 성공",
                        "OK",
                        responseDto
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
        AttendanceStatus attendanceStatus = attendanceService.checkOut(user, requestDto);

        AttendanceStatusResponseDto responseDto = new AttendanceStatusResponseDto(attendanceStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        "퇴실 성공",
                        "OK",
                        responseDto
                ));
    }


}
