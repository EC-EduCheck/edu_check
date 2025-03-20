package org.example.educheck.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.dto.request.AttendanceUpdateRequestDto;
import org.example.educheck.domain.attendance.dto.response.AttendanceListResponseDto;
import org.example.educheck.domain.attendance.dto.response.StudentAttendanceListResponseDto;
import org.example.educheck.domain.attendance.entity.Attendance;
import org.example.educheck.domain.attendance.entity.Status;
import org.example.educheck.domain.attendance.repository.AttendanceRepository;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.campus.Campus;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.lecture.Lecture;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.member.student.repository.StudentRepository;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.global.common.exception.custom.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


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
    private final StaffRepository staffRepository;
    private final StaffCourseRepository staffCourseRepository;
    private final CourseRepository courseRepository;

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

    public AttendanceListResponseDto getTodayAttendances(Long courseId, UserDetails user) {
        // 현재 관리자가 courseId를 가지고 있는지 확인하기
        String email = user.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 관리자가 없습니다."));
        Staff staff = staffRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 관리자가 없습니다."));
        staffCourseRepository.findByStaffIdAndCourseId(staff.getId(), courseId)
                .orElseThrow(() -> new ResourceNotFoundException("관리자가 해당하는 강의를 가지고 있지 않습니다."));

        // 해당 course 에서 오늘 닐짜의 lectureId 확인하기
        LocalDateTime today = LocalDateTime.now();
        Lecture lecture = lectureRepository.findByCourseToday(courseId, today)
                .orElseThrow(() -> new ResourceNotFoundException("교육 과정 중 금일 강의는 존재하지 않습니다."));

        // 해당 lectureId의 출석 리스트 가져오기
        List<Attendance> attendances = attendanceRepository.findAllByLectureId(lecture.getId());

        Map<Status, Long> attendanceCounts = attendances.stream()
                .collect(Collectors.groupingBy(
                        Attendance::getStatus,
                        Collectors.counting())
                );

        long attence = attendanceCounts.getOrDefault(Status.ATTENDANCE, 0L);
        long late = attendanceCounts.getOrDefault(Status.LATE, 0L);
        long earlyLeave = attendanceCounts.getOrDefault(Status.EARLY_LEAVE, 0L);
        long absence = attendanceCounts.getOrDefault(null, 0L);

        return AttendanceListResponseDto.from(staff.getId(), attendances, attence, late, earlyLeave, absence);
    }

    public StudentAttendanceListResponseDto getStudentAttendances(Long courseId, Long studentId, UserDetails user) {
        checkStaffHasCourse(user, courseId);

        // studentId에 해당하는 수강생이 있는지 확인하기
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 수강생이 없습니다."));

        // 해당 교육 강좌에 해당하는 강의 리스트
        List<Lecture> lectures = lectureRepository.findAllByCourseId(courseId);

        // 특정 학생의 강의의 출석 리스트
        List<Attendance> attendances = lectures.stream()
                .map(lecture -> attendanceRepository.findByLectureIdStudentId(studentId, lecture.getId()))
                .toList();

        // 조회한 학생의 금일 기준 출석률
        LocalDateTime today = LocalDateTime.now();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 강의가 없습니다."));
        Long lectureTotalCountByToday = lectureRepository.findByCourseIdAndDateBetween(courseId, course.getStartDate(), today).stream().count();
        Long attendanceTotalCountByToday = attendances.stream().filter(attendance -> attendance.getStatus() == Status.ATTENDANCE).count();
        Long attendanceRateByToday = (attendanceTotalCountByToday / lectureTotalCountByToday) * 100;

        // 조회한 학생의 전체 출석률
        Long overallAttendanceRate = (attendanceTotalCountByToday / (long) attendances.size()) * 100;

        // 과정 진행률
        Long courseProgressRate = (lectureTotalCountByToday / (long) attendances.size()) * 100;

        return StudentAttendanceListResponseDto.from(student, attendances, attendanceRateByToday, overallAttendanceRate, courseProgressRate);
    }

    public void updateStudentAttendance(Long courseId, Long studentId, AttendanceUpdateRequestDto requestDto, UserDetails user) {
        checkStaffHasCourse(user, courseId);

        // 해당 날짜의 강의 구하기
        Lecture lecture = (Lecture) lectureRepository.findAllByCourseId(courseId).stream()
                .filter(lec -> lec.getDate() == requestDto.getDate());

        // 해당 날짜의 출석 구하기
        Attendance attendance = attendanceRepository.findByLectureIdStudentId(studentId, lecture.getId());

        attendance.updateStatus(Status.valueOf(requestDto.getStatus()));
        attendanceRepository.save(attendance);
    }

    public void checkStaffHasCourse(UserDetails user, Long courseId) {
        // 현재 관리자가 courseId를 가지고 있는지 확인하기
        String email = user.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 관리자가 없습니다."));
        Staff staff = staffRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 관리자가 없습니다."));
        staffCourseRepository.findByStaffIdAndCourseId(staff.getId(), courseId)
                .orElseThrow(() -> new ResourceNotFoundException("관리자가 해당하는 강의를 가지고 있지 않습니다."));
    }
}
