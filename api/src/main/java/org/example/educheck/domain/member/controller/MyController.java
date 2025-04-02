package org.example.educheck.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.dto.UpdateMyProfileRequestDto;
import org.example.educheck.domain.member.dto.response.MyProfileResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.service.MyService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    private final MyService myService;

    //        @PreAuthorize("hasAuthority('STUDENT')")
    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<MyProfileResponseDto>> getMyProfile(@AuthenticationPrincipal Member member) {

        log.info(member.getAuthorities().toString());
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "개인 정보 조회 성공",
                        "OK",
                        myService.getMyProfile(member)
                )
        );
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateMyProfile(@AuthenticationPrincipal Member member, @Valid @RequestBody UpdateMyProfileRequestDto requestDto) {

        myService.updateMyProfile(member, requestDto);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "개인 정보 수정 성공",
                        "OK",
                        null
                )
        );
    }
}
