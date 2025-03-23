package org.example.educheck.domain.meetingroomreservation.repository;

import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

}
