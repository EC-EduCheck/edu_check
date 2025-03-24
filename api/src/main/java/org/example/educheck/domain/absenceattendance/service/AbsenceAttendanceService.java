package org.example.educheck.domain.absenceattendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendance.dto.request.CreateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceAttendanceRequestDto;
import org.example.educheck.domain.absenceattendance.dto.response.CreateAbsenceAttendacneReponseDto;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.absenceattendance.repository.AbsenceAttendanceRepository;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.example.educheck.domain.absenceattendanceattachmentfile.repository.AbsenceAttendanceAttachmentFileRepository;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.global.common.exception.custom.common.ResourceMismatchException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.example.educheck.global.common.s3.S3Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AbsenceAttendanceService {
    private final AbsenceAttendanceRepository absenceAttendanceRepository;
    private final StaffRepository staffRepository;
    private final S3Service s3Service;
    private final CourseRepository courseRepository;
    private final AbsenceAttendanceAttachmentFileRepository absenceAttendanceAttachmentFileRepository;

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

    @Transactional
    public CreateAbsenceAttendacneReponseDto createAbsenceAttendance(Member member, Long courseId, CreateAbsenceAttendacneRequestDto requestDto, MultipartFile[] files) {

        //신청자가 해당 course를 수강중인지 확인


        //먼저, 신청내역부터 저장 (순서상 + 연관관계 생성을 위해)
        AbsenceAttendance absenceAttendance = AbsenceAttendance.builder()
                .course(courseRepository.findById(courseId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정을 찾을 수 없습니다.")))
                .student(member.getStudent())
                .startTime(requestDto.getStartDate())
                .endTime(requestDto.getEndDate())
                .reason(requestDto.getResean())
                .category(requestDto.getCategory())
                .build();

        AbsenceAttendance savedAbsenceAttendance = absenceAttendanceRepository.save(absenceAttendance);

        if (files != null && files.length > 0) {
            List<Map<String, String>> uploadedResults = s3Service.uploadFiles(files);
            // fileUrl, s3Key가 List로
            for (Map<String, String> result : uploadedResults) {
                for (MultipartFile file : files) {

                    String originalName = file.getOriginalFilename();
                    String mimeType = file.getContentType();

                    AbsenceAttendanceAttachmentFile attachmentFile = AbsenceAttendanceAttachmentFile.builder()
                            .absenceAttendance(savedAbsenceAttendance)
                            .url(result.get("fileUrl"))
                            .s3Key(result.get("s3Key"))
                            .originalName(originalName)
                            .mime(mimeType)
                            .build();

                    log.info(attachmentFile.getUrl());

                    absenceAttendanceAttachmentFileRepository.save(attachmentFile);
                }

            }
        }

        return CreateAbsenceAttendacneReponseDto.from(savedAbsenceAttendance);


    }
}
