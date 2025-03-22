package org.example.educheck.domain.absenceattendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceAttendanceRequestDto;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.absenceattendance.repository.AbsenceAttendanceRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.global.common.exception.custom.common.ResourceMismatchException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AbsenceAttendanceService {
    private final AbsenceAttendanceRepository absenceAttendanceRepository;
    private final StaffRepository staffRepository;

    @Transactional
    @PreAuthorize("hasAnyAuthority('MIDDLE_ADMIN')")
    public void processAbsenceAttendanceService(Long courseId, Long absesnceAttendancesId, ProcessAbsenceAttendanceRequestDto requestDto, Member member) {


        AbsenceAttendance absenceAttendance =
                absenceAttendanceRepository.findById(absesnceAttendancesId)
                        .orElseThrow(() -> new ResourceNotFoundException("유교 결석 조회 불가"));

        Staff staff =
                staffRepository.findByMember(member)
                        .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));

        if (absenceAttendance.getCourse().getId() != courseId) {
            throw new ResourceMismatchException();
        }

        absenceAttendance.setStaff(staff);
        absenceAttendance.setApproveDate(LocalDateTime.now());
        absenceAttendance.setIsApprove(
                String.valueOf(
                                requestDto.isApprove()
                        )
                        .toUpperCase().charAt(0)
        );
    }
}
