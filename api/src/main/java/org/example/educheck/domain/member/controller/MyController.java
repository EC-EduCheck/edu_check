package org.example.educheck.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    @PostMapping("/attendance-absence")
    public void applyAttendanceAbsence(@AuthenticationPrincipal Member member

    ) {

    }
}
