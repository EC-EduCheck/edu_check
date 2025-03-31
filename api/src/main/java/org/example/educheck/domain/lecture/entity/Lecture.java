package org.example.educheck.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.course.entity.Course;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "lecture",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_courseId_session", columnNames = {"course_id", "session"})
        }
)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private int session;
    private String title;
    //LocalDate로 변경
    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

}
