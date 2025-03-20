package org.example.educheck.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.entity.Attendance;
import org.example.educheck.domain.attendance.entity.Status;
import org.example.educheck.domain.attendance.repository.AttendanceRepository;
import org.example.educheck.domain.campus.Campus;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.lecture.Lecture;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.member.student.repository.StudentRepository;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    private final LectureRepository lectureRepository;
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    private static final double LOCATION_TOLERANCE = 0.001;
    // 출석 인정 마감 시간
    private static final LocalTime ATTENDANCE_DEADLINE = LocalTime.of(23, 30);

    @Transactional
    public Status checkIn(Long studentId, AttendanceCheckinRequestDto requestDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

        if (student.getCourseParticipationStatus() != 'T') {
            throw new IllegalArgumentException("현재 과정에 참여 중이지 않은 학생입니다.");
        }

        Registration currentRegistration = registrationRepository.findByStudentIdAndStatus(
                        studentId, org.example.educheck.domain.registration.entity.Status.PROGRESS)
                .orElseThrow(() -> new IllegalArgumentException("현재 진행 중인 과정 등록이 없습니다."));

        Course currentCourse = currentRegistration.getCourse();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
        Lecture todayLecture = lectureRepository.findByCourseIdAndDateBetween(
                        currentCourse.getId(), startOfDay, endOfDay)
                .orElseThrow(() -> new IllegalArgumentException("오늘 예정된 강의가 없습니다."));

        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(ATTENDANCE_DEADLINE)) {
            createAttendanceRecord(student, todayLecture, Status.LATE);
            return Status.LATE;
        }

        Campus campus = currentCourse.getCampus();
        if (!isWithinCampusArea(campus, requestDto.getLatitude(), requestDto.getLongitude())) {
            throw new IllegalArgumentException("출석 가능한 위치가 아닙니다.");
        }

        createAttendanceRecord(student, todayLecture, Status.ATTENDANCE);
        return Status.ATTENDANCE;
    }

    private void createAttendanceRecord(Student student, Lecture lecture, Status status) {
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setLecture(lecture);
        attendance.setCheckInTimestamp(LocalDateTime.now());
        attendance.setStatus(status);

        attendanceRepository.save(attendance);
    }

    private boolean isWithinCampusArea(Campus campus, double latitude, double longitude) {
        return Math.abs(campus.getGpsY() - latitude) <= LOCATION_TOLERANCE &&
                Math.abs(campus.getGpsX() - longitude) <= LOCATION_TOLERANCE;
    }
    @Transactional
    public Status checkInByEmail(String email, AttendanceCheckinRequestDto requestDto) {
        // 이메일로 Member 찾기
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // Member로 Student 찾기 (StudentRepository에 메소드 추가 필요)
        Student student = studentRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

        // 기존 checkIn 메소드 호출
        return checkIn(student.getId(), requestDto);
    }
}
