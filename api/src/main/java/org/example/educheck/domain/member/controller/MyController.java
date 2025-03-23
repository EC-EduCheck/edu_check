package org.example.educheck.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.dto.response.AttendanceRateDto;
import org.example.educheck.domain.member.service.MyService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/course/{coursdId}/attendances-rate")
    public ResponseEntity<ApiResponse<AttendanceRateDto>> getMyAttendanceRate(@AuthenticationPrincipal UserDetails userDetails,
                                                                              @PathVariable Long coursdId) {

        return ResponseEntity.ok(
                ApiResponse.ok("현재 수강중인 과정 출석 상태 및 출석률 조회",
                        "OK",
                        myService.getMyAttendanceRate(coursdId))
        );

    }
}
