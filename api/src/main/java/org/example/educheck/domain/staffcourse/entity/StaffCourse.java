package org.example.educheck.domain.staffcourse.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.member.staff.entity.Staff;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StaffCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
