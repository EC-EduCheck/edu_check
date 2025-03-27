package org.example.educheck.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.course.entity.Course;

import java.time.LocalDateTime;

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
    private LocalDateTime date;

    @Builder
    public Lecture(Course course, int session, String title, LocalDateTime date) {
        this.course = course;
        this.session = session;
        this.title = title;
        this.date = date;
    }
}
