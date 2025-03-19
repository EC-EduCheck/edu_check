package org.example.educheck.domain.meetingroomreservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.service.MeetingRoomReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/campuses/{campusId}/meeting-rooms/reservations")
@RequiredArgsConstructor
public class MeetingRoomReservationController {

    private final MeetingRoomReservationService meetingRoomReservationService;

    @PostMapping
    public void createReservation(@AuthenticationPrincipal UserDetails user,
                                  @PathVariable Long campusId,
                                  @Valid @RequestBody MeetingRoomReservationRequestDto requestDto) {
        meetingRoomReservationService.createReservation(user, campusId, requestDto);
    }
}
