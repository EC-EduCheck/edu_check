package org.example.educheck.domain.member.staff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.staff.dto.request.UpdateStudentRegistrationStatusRequestDto;
import org.example.educheck.domain.member.staff.dto.response.GetStudentsResponseDto;
import org.example.educheck.domain.member.staff.dto.response.UpdateStudentRegistrationStatusResponseDto;
import org.example.educheck.domain.member.staff.service.StaffService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StaffController {
    private final StaffService staffService;

    @PreAuthorize("hasAuthority('MIDDLE_ADMIN')")
    @PutMapping("/course/{courseId}/student/{studentId}")
    public ResponseEntity<ApiResponse<UpdateStudentRegistrationStatusResponseDto>> updateStudentRegistrationStatus(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @RequestBody UpdateStudentRegistrationStatusRequestDto requestDto) {

        return ResponseEntity.ok(
                ApiResponse.ok("수강생 등록 상태 변경 성공",
                        "OK",
                        staffService.updateStudentRegistrationStatus(member, courseId, studentId, requestDto)));
    }

    @PreAuthorize("hasAuthority('MIDDLE_ADMIN')")
    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<ApiResponse<GetStudentsResponseDto>> getStudentsByCourse(
            @AuthenticationPrincipal Member member,
            @PathVariable Long courseId
            ) {

        return ResponseEntity.ok(
                ApiResponse.ok("수강생 전체 조회 성공",
                        "OK",
                        staffService.getStudentsByCourse(member, courseId)));
    }


}
