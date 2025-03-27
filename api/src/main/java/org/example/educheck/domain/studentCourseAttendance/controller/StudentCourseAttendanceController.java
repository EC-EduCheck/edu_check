package org.example.educheck.domain.studentCourseAttendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.response.MyAttendanceRecordListResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordListResponseDto;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceStatsResponseDto;
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

    private static Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Order.asc("lectureDate")));
    }

    @PreAuthorize("hasAnyAuthority('MIDDLE_ADMIN')")
    @GetMapping("/courses/{courseId}/students/{studentId}/attendances")
    public ResponseEntity<ApiResponse<AttendanceRecordListResponseDto>> getStudentAttendances(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = createPageable(page, size);

        return ResponseEntity.ok(ApiResponse.ok(
                "특정 학생 세부 출결 현황 조회 성공",
                "OK",
                studentCourseAttendanceService.getStudentAttendanceRecordLists(member, studentId, courseId, pageable)
        ));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/my/courses/{courseId}/attendances")
    public ResponseEntity<ApiResponse<MyAttendanceRecordListResponseDto>> getAttendances(@AuthenticationPrincipal Member member,
                                                                                         @PathVariable Long courseId,
                                                                                         @RequestParam(required = false) Integer year,
                                                                                         @RequestParam(required = false) Integer month,
                                                                                         @RequestParam(defaultValue = "0") int page,
                                                                                         @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = createPageable(page, size);

        return ResponseEntity.ok(ApiResponse.ok(
                "출석부 조회 성공",
                "OK",
                studentCourseAttendanceService.getMyAttendanceRecordLists(member, courseId, year, month, pageable)

        ));
    }

    @GetMapping("/my/course/{courseId}/attendances/stats")
    public ResponseEntity<ApiResponse<AttendanceStatsResponseDto>> getAttendancesStats(@AuthenticationPrincipal Member member,
                                                                                       @PathVariable Long courseId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "결석 현황 및 출석률 조회 성공",
                        "OK",
                        studentCourseAttendanceService.getAttendancesStats(member, courseId)

                )
        );
    }

}
