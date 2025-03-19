package org.example.educheck.domain.meetingroom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.campus.Campus;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    private Campus campus;

    private String name;

    @Builder
    public MeetingRoom(String name, Campus campus) {
        this.name = name;
        this.campus = campus;
    }

    public Long getCampusId() {
        return this.campus != null ? this.campus.getId() : null;
    }
}
