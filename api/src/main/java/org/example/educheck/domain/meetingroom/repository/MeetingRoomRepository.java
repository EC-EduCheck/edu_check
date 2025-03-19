package org.example.educheck.domain.meetingroom.repository;

import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    @Query("SELECT mr FROM MeetingRoom mr JOIN FETCH mr.campus WHERE mr.id = :id")
    MeetingRoom findByIdWithCampus(@Param("id") Long id);
}
