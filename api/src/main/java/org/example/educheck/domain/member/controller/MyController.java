package org.example.educheck.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.absenceattendance.dto.request.CreateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.service.AbsenceAttendanceService;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private final AbsenceAttendanceService absenceAttendanceService;

    @PostMapping("/attendance-absence")
    public void applyAttendanceAbsence(@AuthenticationPrincipal Member member,
                                       @RequestPart(value = "data") CreateAbsenceAttendacneRequestDto requestDto,
                                       @RequestPart(value = "file", required = false) MultipartFile file

    ) {
        absenceAttendanceService.createAbsenceAttendance(member, requestDto, file);
    }
}
