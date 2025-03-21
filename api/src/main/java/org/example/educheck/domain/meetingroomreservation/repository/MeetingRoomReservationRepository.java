package org.example.educheck.domain.meetingroomreservation.repository;

import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface MeetingRoomReservationRepository extends JpaRepository<MeetingRoomReservation, Long> {

    @Query("SELECT COUNT(r) > 0 FROM MeetingRoomReservation r WHERE r.meetingRoom = :meetingRoom " +
            "AND FUNCTION('DATE',r.startTime)  = :date " +
            "AND ((r.startTime <= :endTime AND r.endTime > :startTime) " +
            "OR (r.startTime < :endTime AND r.endTime >= :endTime) " +
            "OR (r.startTime >= :startTime AND r.endTime <= :endTime))")
    boolean existsOverlappingReservation(@Param("meetingRoom") MeetingRoom meetingRoom,
                                         @Param("date") LocalDate data,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);

    @Query("SELECT r FROM MeetingRoomReservation r " +
            "JOIN FETCH r.member m " +
            "JOIN FETCH r.meetingRoom mr " +
            "WHERE r.id = :reservationId")
    Optional<MeetingRoomReservation> findByIdWithDetails(@Param("reservationId") Long reservationId);


}
