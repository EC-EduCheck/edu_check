package org.example.educheck.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.response.StudentAttendanceListResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffAttendanceService {

    public StudentAttendanceListResponseDto getStudentAttendances(Member member, Long courseId, Long studentId) {
    }
}
