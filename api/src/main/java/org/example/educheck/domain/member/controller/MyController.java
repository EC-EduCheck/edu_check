package org.example.educheck.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.service.MyService;
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
    public void getMyAttendanceRate(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long coursdId) {
        myService.getMyAttendanceRate(userDetails, coursdId);
    }
}
