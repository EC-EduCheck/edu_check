package org.example.educheck.domain.lecture.repository;

import jakarta.persistence.EntityManager;
import org.example.educheck.domain.attendance.entity.Attendance;
import org.example.educheck.domain.attendance.entity.Status;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.lecture.Lecture;
import org.example.educheck.domain.member.dto.response.AttendanceRateProjection;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.student.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:mysql:8.0://localhost/test",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
class LectureRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    void findAttendanceRateByCourse_calculateCorrectly() {
        //given
        Member member = createAndPersistMember();
        Student student = createAndPersistStudent(member);
        Course course = createAndPersistCourse();

        List<Lecture> lectureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lectureList.add(createAndPersistLecture(course, LocalDateTime.now().plusDays(i)));
        }

        for (int i = 0; i < 5; i++) {
            createAndPersistAttendance(student, lectureList.get(i), lectureList.get(i).getDate(), Status.ATTENDANCE);
        }

        for (int i = 5; i < 7; i++) {
            createAndPersistAttendance(student, lectureList.get(i), lectureList.get(i).getDate(), Status.LATE);
        }

        createAndPersistAttendance(student, lectureList.get(9), lectureList.get(9).getDate(), Status.EARLY_LEAVE);

        em.flush();
        em.clear();

        String nativeQuery = """
                SELECT *
                FROM lecture l
                LEFT JOIN attendance a ON DATE(l.date) = DATE(a.check_in_timestamp)
                LEFT JOIN student s ON a.student_id = s.id
                LEFT JOIN member m ON s.member_id = m.id
                WHERE l.course_id = :courseId
                AND m.id = :memberId
                """;

        List<Object[]> results = em.createNativeQuery(nativeQuery)
                .setParameter("courseId", course.getId())
                .setParameter("memberId", member.getId())
                .getResultList();

        for (Object[] row : results) {
            Lecture lecture = (Lecture) row[0];
            Attendance attendance = (Attendance) row[1];
            Student student2 = (Student) row[2];
            Member member2 = (Member) row[3];

            System.out.println("Lecture ID: " + lecture.getId());
            System.out.println("Attendance Status: " + (attendance != null ? attendance.getStatus() : "No record"));
            System.out.println("Student Name: " + student2.getMember().getName());
        }

        //when
        AttendanceRateProjection result = lectureRepository.findAttendanceRateByCourse(course.getId(), member.getId());

        //then
        //출석 5, 지각2, 조퇴1, 결석2 => 총결석 3, 출석률 70%, 출석 횟수 8
        assertNotNull(result);
        assertEquals(student.getId(), result.getStudentId());
        assertEquals(member.getId(), result.getMemberId());
        assertEquals(8, result.getAttendanceCount().intValue()); //SQL 쿼리의 ROUNT() -> BigDecimal 타입 반환
        assertEquals(70, result.getAttendanceRate().intValue());
        assertEquals(2, result.getAbsenceCount().intValue());
        assertEquals(3, result.getAdjustedAbsenceCount().intValue());
        assertEquals(1, result.getEarlyLeaveCount().intValue());
        assertEquals(3, result.getTotalAttendance().intValue());


    }

    private Attendance createAndPersistAttendance(Student student, Lecture lecture, LocalDateTime dateTime, Status status) {
        Attendance attendance = Attendance.builder()
                .student(student)
                .lecture(lecture)
                .checkInTimestamp(dateTime)
                .build();
        em.persist(attendance);
        return attendance;
    }

    private Lecture createAndPersistLecture(Course course, LocalDateTime date) {
        Lecture lecture = Lecture.builder()
                .course(course)
                .date(date)
                .build();
        em.persist(lecture);
        return lecture;
    }

    private Course createAndPersistCourse() {
        Course course = Course.builder()
                .name("Spring Java React Course")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(2))
                .build();
        em.persist(course);
        return course;
    }

    private Student createAndPersistStudent(Member member) {
        Student student = Student.builder()
                .member(member)
                .build();
        em.persist(student);
        return student;
    }

    private Member createAndPersistMember() {
        Member member = Member.builder()
                .name("홍길동")
                .email("hong@naver.com")
                .build();
        em.persist(member);
        return member;
    }
}