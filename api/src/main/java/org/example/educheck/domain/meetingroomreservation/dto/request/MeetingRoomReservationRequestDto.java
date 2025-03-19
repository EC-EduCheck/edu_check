package org.example.educheck.domain.meetingroomreservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomReservationRequestDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long meetingRoomId;
    private long courseId;

    public MeetingRoomReservation toEntity(Member member, MeetingRoom meetingRoom) {
        return MeetingRoomReservation.builder()
                .member(member)
                .meetingRoom(meetingRoom)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
