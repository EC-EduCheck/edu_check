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
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.global.common.exception.custom.common.NotOwnerException;
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
    private final RegistrationRepository registrationRepository;

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

        validateRegistrationCourse(member, courseId);

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

        saveAttachementFiles(files, savedAbsenceAttendance);

        return CreateAbsenceAttendacneReponseDto.from(savedAbsenceAttendance);
    }

    private void saveAttachementFiles(MultipartFile[] files, AbsenceAttendance savedAbsenceAttendance) {
        if (files != null && files.length > 0) {
            List<Map<String, String>> uploadedResults = s3Service.uploadFiles(files);
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
    }

    private void validateRegistrationCourse(Member member, Long courseId) {
        log.info("memberId: " + member.getId() + " courseId: " + courseId);
        Student student = member.getStudent();
        log.info("studentId : {}", student.getId());
        Registration registration = registrationRepository.findByStudentIdAndCourseId(member.getStudent().getId(), courseId)
                .orElseThrow(ResourceNotFoundException::new);

        if (registration == null) {
            throw new ResourceMismatchException();
        }
    }

    public void cancelAttendanceAbsence(Member member, Long absenceAttendancesId) {

        AbsenceAttendance absenceAttendance = absenceAttendanceRepository.findById(absenceAttendancesId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유고 결석 신청 내역이 존재하지 않습니다."));

        validateIsApplicant(member, absenceAttendance);

        //해당 첨부파일 삭제? -> 삭제 처리 -> 양방향 필요
//        absenceAttendance.

        //신청 내역 소프트 딜리트 처리
        absenceAttendanceRepository.
    }

    private static void validateIsApplicant(Member member, AbsenceAttendance absenceAttendance) {
        //member랑 신청자가 일치하는지 확인
        if (!member.getStudent().equals(absenceAttendance.getStudent())) {
            throw new NotOwnerException();
        }
    }
}
