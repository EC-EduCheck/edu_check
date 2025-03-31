package org.example.educheck.domain.meetingroomreservation.repository;

import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroomreservation.dto.response.MeetingRoomReservationsProjections;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRoomReservationRepository extends JpaRepository<MeetingRoomReservation, Long> {

    @Query("SELECT COUNT(r) > 0 FROM MeetingRoomReservation r WHERE r.meetingRoom = :meetingRoom " +
            "AND r.status = :status " +
            "AND FUNCTION('DATE',r.startTime)  = :date " +
            "AND ((r.startTime <= :endTime AND r.endTime > :startTime) " +
            "OR (r.startTime < :endTime AND r.endTime >= :endTime) " +
            "OR (r.startTime >= :startTime AND r.endTime <= :endTime))")
    boolean existsOverlappingReservation(@Param("meetingRoom") MeetingRoom meetingRoom,
                                         @Param("date") LocalDate data,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime,
                                         @Param("status") ReservationStatus status);

    @Query("SELECT r FROM MeetingRoomReservation r " +
            "JOIN FETCH r.member m " +
            "JOIN FETCH r.meetingRoom mr " +
            "WHERE r.id = :reservationId")
    Optional<MeetingRoomReservation> findByIdWithDetails(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM MeetingRoomReservation r " +
            "WHERE r.status = :status " +
            "AND r.id = :reservationId")
    Optional<MeetingRoomReservation> findByStatusAndById(@Param("reservationId") Long reservationId,
                                                         @Param("status") ReservationStatus status);

    @Query(value = """
                    SELECT
                    COALESCE(SUM(TIMESTAMPDIFF(MINUTE , r.end_time, r.start_time)),0)
                    FROM meeting_room_reservation r
                    WHERE r.memeber_id = :memberId
                    AND r.status = 'ACTIVE'
                    AND DATE(r.start_time) = DATE(NOW())
            """, nativeQuery = true)
    int getTotalReservationMinutesForMember(@Param("memberId") Long memberId);

    @Query(value = """
                SELECT
                    m.id AS meetingRoomId,
                    m.name AS meetingRoomName,
                    m.campus_id AS campusId,
                    r.id AS meetingRoomReservationId,
                    me.id AS memberId,
                    me.name AS memberName,
                    r.status AS reservationStatus,
                    r.start_time AS startTime,
                    r.end_time AS endTime
                FROM meeting_room m
                LEFT JOIN meeting_room_reservation r
                    ON m.id = r.meeting_room_id
                LEFT JOIN member me
                    ON r.memeber_id = me.id
                WHERE m.campus_id = :campusId
                  # 테스트용 날짜 잠시 수정
                 AND (DATE(r.start_time) = DATE('2025-03-30') OR r.start_time IS NULL)
                 AND (r.status = 'ACTIVE' OR r.status IS NULL)
                ORDER BY r.start_time
            """, nativeQuery = true)
    List<MeetingRoomReservationsProjections> findByCampusId(@Param("campusId") Long campusId);


}
