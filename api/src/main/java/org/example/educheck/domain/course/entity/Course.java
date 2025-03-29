package org.example.educheck.domain.course.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.campus.Campus;

import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Course(Campus campus, String name, LocalDate startDate, LocalDate endDate) {
        this.campus = campus;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
