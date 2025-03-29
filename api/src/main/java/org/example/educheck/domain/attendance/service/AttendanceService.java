package org.example.educheck.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.request.AttendanceCheckinRequestDto;
import org.example.educheck.domain.attendance.dto.request.AttendanceUpdateRequestDto;
import org.example.educheck.domain.attendance.entity.Attendance;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;
import org.example.educheck.domain.attendance.repository.AttendanceRepository;
import org.example.educheck.domain.campus.Campus;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.lecture.entity.Lecture;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.member.student.repository.StudentRepository;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.entity.RegistrationStatus;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
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
    private static final double LOCATION_TOLERANCE = 0.05;
    private static final LocalTime ATTENDANCE_DEADLINE = LocalTime.of(9, 30);
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    private final LectureRepository lectureRepository;
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    private final StaffCourseRepository staffCourseRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public AttendanceStatus checkIn(UserDetails user, AttendanceCheckinRequestDto requestDto) {
        if (user == null) {
            throw new IllegalArgumentException("인증된 사용자 정보가 없습니다.");
        }

        String userEmail = user.getUsername();

        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Student student = studentRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

        Long studentId = student.getId();

        if (student.getCourseParticipationStatus() != 'T') {
            throw new IllegalArgumentException("현재 과정에 참여 중이지 않은 학생입니다.");
        }

        Registration currentRegistration = registrationRepository.findByStudentIdAndRegistrationStatus(
                        studentId, RegistrationStatus.PROGRESS)
                .orElseThrow(() -> new IllegalArgumentException("현재 진행 중인 과정 등록이 없습니다."));
        Course currentCourse = currentRegistration.getCourse();

        LocalDate today = LocalDate.now();
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDate startOfDay = today;
//        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
//        Lecture todayLecture = lectureRepository.findByCourseIdAndDateBetween(
//                        currentCourse.getId(), startOfDay, endOfDay)
//                .orElseThrow(() -> new IllegalArgumentException("오늘 예정된 강의가 없습니다."));
        Lecture todayLecture = lectureRepository.findByCourseIdAndDate(
                        currentCourse.getId(), today)
                .orElseThrow(() -> new IllegalArgumentException("오늘 예정된 강의가 없습니다."));

        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(ATTENDANCE_DEADLINE)) {
            createAttendanceRecord(student, todayLecture, AttendanceStatus.LATE);
            return AttendanceStatus.LATE;
        }

        Campus campus = currentCourse.getCampus();

        if (!isWithinCampusArea(campus, requestDto.getLatitude(), requestDto.getLongitude())) {
            throw new IllegalArgumentException("출석 가능한 위치가 아닙니다.");
        }

//        Attendance attendance = attendanceRepository.findByStudentIdAndCheckInTimestampBetween(
//                        student.getId(), startOfDay, endOfDay)
//                .orElseThrow(() -> new IllegalArgumentException("금일 출석 기록이 없습니다."));

        Attendance attendance = attendanceRepository.findByStudentIdAndCheckInExist(
                        student.getId(), today)
                .orElseThrow(() -> new IllegalArgumentException("금일 출석 기록이 없습니다.")
                );

        if (attendance.getCheckInTimestamp() != null) {
            throw new IllegalStateException("이미 출석 처리되었습니다.");
        }

        createAttendanceRecord(student, todayLecture, AttendanceStatus.ATTENDANCE);
        return AttendanceStatus.ATTENDANCE;
    }

    private void createAttendanceRecord(Student student, Lecture lecture, AttendanceStatus attendanceStatus) {
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setLecture(lecture);
        attendance.setCheckInTimestamp(LocalDateTime.now());
        attendance.setAttendanceStatus(attendanceStatus);

        attendanceRepository.save(attendance);
    }

    //TODO: 좌표 계산 원으로
    private boolean isWithinCampusArea(Campus campus, double latitude, double longitude) {
        boolean isWithin = Math.abs(campus.getGpsY() - latitude) <= LOCATION_TOLERANCE &&
                Math.abs(campus.getGpsX() - longitude) <= LOCATION_TOLERANCE;
        return isWithin;
    }

    private Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 강의가 없습니다."));
    }

    public void updateStudentAttendance(Long courseId, Long studentId, AttendanceUpdateRequestDto requestDto, UserDetails user) {
        checkStaffHasCourse(user, courseId);

        // 해당 날짜의 강의 구하기
        Lecture lecture = (Lecture) lectureRepository.findAllByCourseId(courseId).stream()
                .filter(lec -> lec.getDate() == requestDto.getDate());

        // 해당 날짜의 출석 구하기
        Attendance attendance = attendanceRepository.findByLectureIdStudentId(studentId, lecture.getId());

        attendance.updateStatus(AttendanceStatus.valueOf(requestDto.getStatus()));
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

    @Transactional
    public AttendanceStatus checkOut(UserDetails user, AttendanceCheckinRequestDto requestDto) {
        String email = user.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("사용자 정보를 찾을 수 없습니다."));

        Student student = studentRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new ResourceNotFoundException("학생 정보를 찾을 수 없습니다."));

        Long studentId = student.getId();

        if (student.getCourseParticipationStatus() != 'T') {
            throw new IllegalArgumentException("현재 과정에 참여 중이지 않은 학생입니다.");
        }

        Registration currentRegistration = registrationRepository.findByStudentIdAndRegistrationStatus(
                        studentId, RegistrationStatus.PROGRESS)
                .orElseThrow(() -> new IllegalArgumentException("현재 진행 중인 과정 등록이 없습니다."));
        Course currentCourse = currentRegistration.getCourse();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);

        Attendance attendance = attendanceRepository.findByStudentIdAndCheckInTimestampBetween(
                        student.getId(), startOfDay, endOfDay)
                .orElseThrow(() -> new IllegalArgumentException("금일 출석 기록이 없습니다."));

//        Campus campus = currentCourse.getCampus();
//
//        if (!isWithinCampusArea(campus, requestDto.getLatitude(), requestDto.getLongitude())) {
//            throw new IllegalArgumentException("퇴실 가능한 위치가 아닙니다.");
//        }

        if (attendance.getCheckOutTimestamp() != null) {
            throw new IllegalStateException("이미 퇴실 처리되었습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        attendance.setCheckOutTimestamp(now);

        LocalTime earlyLeaveTime = LocalTime.of(10, 0);
        if (now.toLocalTime().isBefore(earlyLeaveTime)) {
            attendance.setAttendanceStatus(AttendanceStatus.EARLY_LEAVE);
        } else if (attendance.getAttendanceStatus() == AttendanceStatus.LATE) {
            attendance.setAttendanceStatus(AttendanceStatus.LATE);
        } else {
            attendance.setAttendanceStatus(AttendanceStatus.ATTENDANCE);
        }

        attendanceRepository.save(attendance);
        return attendance.getAttendanceStatus();
    }


}
