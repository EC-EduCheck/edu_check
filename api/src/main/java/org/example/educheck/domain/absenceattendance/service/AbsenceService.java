package org.example.educheck.domain.absenceattendance.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceRequestDto;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.absenceattendance.repository.AbsenceAttendanceRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbsenceService {
    private final AbsenceAttendanceRepository absenceAttendanceRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public T processAbsenceService(Long courseId, Long studentId, ProcessAbsenceRequestDto requestDto, Member member) {

        AbsenceAttendance absenceAttendance =
                absenceAttendanceRepository.findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException("유교 결석 조회 불가"));

        Staff staff =
                staffRepository.findByMember(member)
                        .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));

        absenceAttendance.setStaff(staff);
        absenceAttendance.setIsApprove(String.valueOf(requestDto.isApprove()).charAt(0));

    }
}
