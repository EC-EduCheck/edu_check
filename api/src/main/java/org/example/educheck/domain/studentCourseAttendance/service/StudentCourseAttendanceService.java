package org.example.educheck.domain.studentCourseAttendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordListResponseDto;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordResponseDto;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.example.educheck.domain.studentCourseAttendance.repository.StudentCourseAttendanceRepository;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseAttendanceService {

    private final StudentCourseAttendanceRepository studentCourseAttendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceRecordListResponseDto getStudentAttendanceRecordLists(Member member, Long studentId, Long courseId) {

        //임시
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 학생입니다."));

        //TODO 유효성 검증
        List<StudentCourseAttendance> attendanceRecordList = studentCourseAttendanceRepository.findByIdStudentIdAndIdCourseId(studentId, courseId);

        String courseName = attendanceRecordList.isEmpty()
                ? "null"  // 혹은 예외 처리
                : attendanceRecordList.getFirst().getCourseName();

        List<AttendanceRecordResponseDto> list = attendanceRecordList
                .stream()
                .map(AttendanceRecordResponseDto::from)
                .toList();

        return AttendanceRecordListResponseDto.builder()
                .requesterId(member.getId())
                .requesterName(member.getName())
                .studentId(studentId)
                .studentName(student.getName())
                .studentPhoneNumber(student.getPhoneNumber())
                .courseId(courseId)
                .courseName(courseName)
                .attendanceRecordList(list)
                .build();
    }


}
