package org.example.educheck.domain.studentCourseAttendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordListResponseDto;
import org.example.educheck.domain.studentCourseAttendance.service.StudentCourseAttendanceService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentCourseAttendanceController {

    private final StudentCourseAttendanceService studentCourseAttendanceService;

    @GetMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<AttendanceRecordListResponseDto>> getStudentAttendances(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 세부 출결 현황 조회 성공",
                "OK",
                studentCourseAttendanceService.getStudentAttendanceRecordLists(member, studentId, courseId)
        ));
    }
}
