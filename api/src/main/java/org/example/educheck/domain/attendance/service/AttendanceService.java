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

    private static final double LOCATION_TOLERANCE = 0.001;
    private static final LocalTime ATTENDANCE_DEADLINE = LocalTime.of(9, 30);

    @Transactional
    public void checkIn(Long studentId, AttendanceCheckinRequestDto requestDto) {
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
        Lecture todayLecture = lectureRepository.findByCourseIdAndDate(currentCourse.getId(), today.atStartOfDay())
                .orElseThrow(() -> new IllegalArgumentException("오늘 예정된 강의가 없습니다."));

        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(ATTENDANCE_DEADLINE)) {
            createAttendanceRecord(student, todayLecture, Status.LATE);
            return;
        }

        Campus campus = currentCourse.getCampus();
        if (!isWithinCampusArea(campus, requestDto.getLatitude(), requestDto.getLongitude())) {
            throw new IllegalArgumentException("출석 가능한 위치가 아닙니다.");
        }

        createAttendanceRecord(student, todayLecture, Status.ATTENDANCE);
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
        // 캠퍼스 GPS 좌표와 학생 위치 비교 (허용 오차 범위 내에 있는지)
        return Math.abs(campus.getGpsY() - latitude) <= LOCATION_TOLERANCE &&
                Math.abs(campus.getGpsX() - longitude) <= LOCATION_TOLERANCE;
    }
}
