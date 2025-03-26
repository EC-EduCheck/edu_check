package org.example.educheck.domain.studentCourseAttendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordListResponseDto;
import org.example.educheck.domain.studentCourseAttendance.service.StudentCourseAttendanceService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentCourseAttendanceController {

    private final StudentCourseAttendanceService studentCourseAttendanceService;

    @PreAuthorize("hasAnyAuthority('MIDDLE_ADMIN')")
    @GetMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<AttendanceRecordListResponseDto>> getStudentAttendances(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("lectureDate")));

        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 세부 출결 현황 조회 성공",
                "OK",
                studentCourseAttendanceService.getStudentAttendanceRecordLists(member, studentId, courseId, pageable)
        ));
    }
}
