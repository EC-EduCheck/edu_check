package org.example.educheck.domain.meetingroomreservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.global.common.entity.BaseTimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "meeting_room_reservation",
        indexes = {
                @Index(name = "idx_meeting_room_availability", columnList = "meeting_room_id, start_time, end_time")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoomReservation extends BaseTimeEntity {

    private static final Logger log = LoggerFactory.getLogger(MeetingRoomReservation.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memeber_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder
    public MeetingRoomReservation(Member member, MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        this.member = member;
        this.meetingRoom = meetingRoom;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.ACTIVE;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }
}
