package org.example.educheck.domain.absenceattendance.controller;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.absenceattendance.dto.request.CreateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceAttendanceRequestDto;
import org.example.educheck.domain.absenceattendance.dto.request.UpdateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.dto.response.*;
import org.example.educheck.domain.absenceattendance.service.AbsenceAttendanceService;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AbsenceAttendanceController {
    private final AbsenceAttendanceService absenceAttendanceService;


    @PatchMapping("/course/{courseId}/absence-attendances/{absesnceAttendancesId}")
    public ResponseEntity<ApiResponse<Void>> processAbsenceAttendanceService(@PathVariable Long courseId, @PathVariable Long absesnceAttendancesId,
                                                                             @RequestBody ProcessAbsenceAttendanceRequestDto requestDto,
                                                                             @AuthenticationPrincipal Member member) {

        absenceAttendanceService.processAbsenceAttendanceService(courseId, absesnceAttendancesId, requestDto, member);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok("유고 결석 처리 성공", "OK", null));
    }


    @GetMapping("/course/{courseId}/absence-attendances")
    public ResponseEntity<ApiResponse<GetAbsenceAttendancesResponseDto>> getAbsenceAttendances(
            @PathVariable Long courseId, @PageableDefault(sort = "startTime",
            direction = Sort.Direction.DESC,
            size = 10)
    Pageable pageable, @AuthenticationPrincipal Member member) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                                "특정 교육 과정 유고 결석 내역 조회 성공",
                                "OK", absenceAttendanceService.getAbsenceAttendances(courseId, pageable, member)
                        )
                );
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/my/course/{courseId}/absence-attendances")
    public ResponseEntity<ApiResponse<CreateAbsenceAttendanceResponseDto>> applyAttendanceAbsence(@AuthenticationPrincipal Member member,
                                                                                                  @PathVariable Long courseId,
                                                                                                  @RequestPart(value = "data") CreateAbsenceAttendacneRequestDto requestDto,
                                                                                                  @RequestPart(value = "files", required = false) MultipartFile[] files

    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("유고 결석 신청 성공",
                        "CREATED",
                        absenceAttendanceService.createAbsenceAttendance(member, courseId, requestDto, files)))
                ;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PutMapping("/my/course/{courseId}/absence-attendances/{absenceAttendancesId}")
    public ResponseEntity<ApiResponse<UpdateAbsenceAttendanceReponseDto>> updateAttendanceAbsence(@AuthenticationPrincipal Member member,
                                                                                                  @PathVariable Long absenceAttendancesId,
                                                                                                  @RequestPart(value = "data") UpdateAbsenceAttendacneRequestDto requestDto,
                                                                                                  @RequestPart(value = "files", required = false) MultipartFile[] files) {


        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("유고 결석 신청 수정 성공",
                        "OK",
                        absenceAttendanceService.updateAttendanceAbsence(member, absenceAttendancesId, requestDto, files)));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @DeleteMapping("/my/course/{courseId}/absence-attendances/{absenceAttendancesId}")
    public ResponseEntity<ApiResponse<Object>> cancelAttendanceAbsence(@AuthenticationPrincipal Member member,
                                                                       @PathVariable Long absenceAttendancesId) {
        absenceAttendanceService.cancelAttendanceAbsence(member, absenceAttendancesId);
        return ResponseEntity.ok(
                ApiResponse.ok("유고 결석 신청 취소 성공",
                        "OK",
                        null));

    }

    @PreAuthorize("hasAnyAuthority('STUDENT','MIDDLE_ADMIN')")
    @GetMapping("/course/{courseId}/absence-attendances/{absenceAttendancesId}")
    public ResponseEntity<ApiResponse<AbsenceAttendanceResponseDto>> c(@AuthenticationPrincipal Member member,
                                                                       @PathVariable Long courseId,
                                                                       @PathVariable Long absenceAttendancesId
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok("유고 결석 신청 내역 상세 조회 성공",
                        "OK",
                        absenceAttendanceService.getAbsenceAttendance(member, courseId, absenceAttendancesId))
        );
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/my/course/{courseId}/absence-attendances")
    public ResponseEntity<ApiResponse<Page<MyAbsenceAttendanceResponseDto>>> getMyAbsenceAttendances(@AuthenticationPrincipal Member member,
                                                                                                     @PathVariable Long courseId,
                                                                                                     @PageableDefault(sort = "createdAt",
                                                                                                             direction = Sort.Direction.DESC,
                                                                                                             size = 4) Pageable pageable) {


        return ResponseEntity.ok(
                ApiResponse.ok("유고 결석 신청 목록 조회 성공",
                        "OK",
                        absenceAttendanceService.getMyAbsenceAttendances(member, courseId, pageable))

        );
    }


}
