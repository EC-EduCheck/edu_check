package org.example.educheck.domain.meetingroomreservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.dto.response.CampusMeetingRoomsDto;
import org.example.educheck.domain.meetingroomreservation.dto.response.MeetingRoomReservationResponseDto;
import org.example.educheck.domain.meetingroomreservation.service.MeetingRoomReservationService;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/campuses/{campusId}/meeting-rooms/reservations")
@RequiredArgsConstructor
public class MeetingRoomReservationController {

    private final MeetingRoomReservationService meetingRoomReservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<MeetingRoomReservationResponseDto>> createReservation(@AuthenticationPrincipal UserDetails userDetails,
                                                                                            @PathVariable Long campusId,
                                                                                            @Valid @RequestBody MeetingRoomReservationRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.ok(
                                "회의실 예약 성공",
                                "OK",
                                meetingRoomReservationService.createReservation(userDetails, campusId, requestDto)
                        )
                );
    }

    @GetMapping("/{meetingRoomReservationId}")
    public ResponseEntity<ApiResponse<MeetingRoomReservationResponseDto>> getReservationById(@AuthenticationPrincipal UserDetails userDetails,
                                                                                             @PathVariable Long meetingRoomReservationId) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "예약 조회 성공",
                        "OK",
                        meetingRoomReservationService.getMeetingRoomReservationById(meetingRoomReservationId)
                )
        );

    }

    @PreAuthorize("hasAnyAuthority('STUDENT','MIDDLE_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<CampusMeetingRoomsDto>> getReservations(@AuthenticationPrincipal Member member,
                                                                              @PathVariable Long campusId,
                                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {

        log.info("date: {}", date);
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "회의실 예약 내역 조회 성공",
                        "OK",
                        meetingRoomReservationService.getMeetingRoomReservations(campusId, date)
                )
        );
    }


    @DeleteMapping("/{meetingRoomReservationId}")
    public ResponseEntity<ApiResponse<Object>> cancelReservation(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable Long meetingRoomReservationId) {
        meetingRoomReservationService.cancelReservation(userDetails, meetingRoomReservationId);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "회의실 예약 취소 성공",
                        "OK",
                        null
                )
        );
    }
}
