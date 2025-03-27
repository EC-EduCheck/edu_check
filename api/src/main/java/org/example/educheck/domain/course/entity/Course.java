package org.example.educheck.domain.course.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.campus.Campus;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    private Campus campus;

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int totalLectureCount;

    @Builder
    public Course(Campus campus, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.campus = campus;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.totalLectureCount = 0;
    }


}
