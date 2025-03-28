package org.example.educheck.domain.absenceattendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendance.dto.request.CreateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.dto.request.ProcessAbsenceAttendanceRequestDto;
import org.example.educheck.domain.absenceattendance.dto.request.UpdateAbsenceAttendacneRequestDto;
import org.example.educheck.domain.absenceattendance.dto.response.*;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.absenceattendance.repository.AbsenceAttendanceRepository;
import org.example.educheck.domain.absenceattendanceattachmentfile.dto.response.AttachmentFileReposeDto;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.example.educheck.domain.absenceattendanceattachmentfile.repository.AbsenceAttendanceAttachmentFileRepository;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.entity.Role;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.global.common.exception.custom.common.*;
import org.example.educheck.global.common.s3.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AbsenceAttendanceService {
    private final AbsenceAttendanceRepository absenceAttendanceRepository;
    private final StaffRepository staffRepository;
    private final StaffCourseRepository staffCourseRepository;
    private final S3Service s3Service;
    private final CourseRepository courseRepository;
    private final AbsenceAttendanceAttachmentFileRepository absenceAttendanceAttachmentFileRepository;
    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;

    private static void validateMatchApplicant(Member member, AbsenceAttendance absenceAttendance) {

        if (!Objects.equals(member.getStudentId(), absenceAttendance.getStudent().getId())) {
            throw new NotOwnerException();
        }
    }

    private static void validateModifiable(AbsenceAttendance absenceAttendance) {

        if (absenceAttendance.getDeletionRequestedAt() != null) {
            throw new InvalidRequestException("이미 취소 요청한 유고 결석 신청입니다.");
        }

        if (absenceAttendance.getIsApprove() != null || absenceAttendance.getApproveDate() != null) {
            throw new InvalidRequestException("처리 이전에만 수정 가능합니다.");
        }
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('MIDDLE_ADMIN')")
    public void processAbsenceAttendanceService(Long courseId, Long absenceAttendancesId, ProcessAbsenceAttendanceRequestDto requestDto, Member member) {


        AbsenceAttendance absenceAttendance =
                absenceAttendanceRepository.findById(absenceAttendancesId)
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

    @PreAuthorize("hasAnyAuthority('MIDDLE_ADMIN')")
    public GetAbsenceAttendancesResponseDto getAbsenceAttendances(Long courseId, Pageable pageable, Member member) {

        // TODO
//        Staff staff =
//                staffRepository.findByMember(member)
//                        .orElseThrow(() -> new ResourceNotFoundException("회원 정보를 찾을 수 없습니다."));
//
//        staffCourseRepository.findByStaffIdAndCourseId(staff.getId(), courseId)
//                .orElseThrow(ForbiddenException::new);

        Page<AbsenceAttendance> attendances = absenceAttendanceRepository.findByCourseId(courseId, pageable);

        return GetAbsenceAttendancesResponseDto.from(attendances, member);
    }

    @Transactional
    public CreateAbsenceAttendanceResponseDto createAbsenceAttendance(Member member, Long courseId, CreateAbsenceAttendacneRequestDto requestDto, MultipartFile[] files) {

        validateRegistrationCourse(member, courseId);

        AbsenceAttendance absenceAttendance = AbsenceAttendance.builder()
                .course(courseRepository.findById(courseId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정을 찾을 수 없습니다.")))
                .student(member.getStudent())
                .startTime(requestDto.getStartDate())
                .endTime(requestDto.getEndDate())
                .reason(requestDto.getReason())
                .category(requestDto.getCategory())
                .build();

        AbsenceAttendance savedAbsenceAttendance = absenceAttendanceRepository.save(absenceAttendance);

        saveAttachementFiles(files, savedAbsenceAttendance);

        return CreateAbsenceAttendanceResponseDto.from(savedAbsenceAttendance);
    }

    private void saveAttachementFiles(MultipartFile[] files, AbsenceAttendance savedAbsenceAttendance) {
        log.info("첨부파일 저장 로직 동작");
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
        Student student = member.getStudent();

        Registration registration = registrationRepository.findByStudentIdAndCourseId(member.getStudent().getId(), courseId)
                .orElseThrow(ResourceNotFoundException::new);

        if (registration == null) {
            throw new ResourceMismatchException();
        }
    }

    @Transactional
    public void cancelAttendanceAbsence(Member member, Long absenceAttendancesId) {

        AbsenceAttendance absenceAttendance = getAbsenceAttendance(absenceAttendancesId);
        validateMatchApplicant(member, absenceAttendance);

        markAttachementFilesForDeletion(absenceAttendance);

        absenceAttendance.markDeletionRequested();
        absenceAttendanceRepository.save(absenceAttendance);
    }


    @Transactional
    public UpdateAbsenceAttendacneReponseDto updateAttendanceAbsence(Member member, Long absenceAttendancesId, UpdateAbsenceAttendacneRequestDto requestDto, MultipartFile[] files) {

        AbsenceAttendance absenceAttendance = getAbsenceAttendance(absenceAttendancesId);
        validateMatchApplicant(member, absenceAttendance);
        validateModifiable(absenceAttendance);
        if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
            throw new InvalidRequestException("시작일은 종료일 이후일 수 없습니다.");
        }

        markAttachementFilesForDeletion(absenceAttendance);

        requestDto.updateEntity(absenceAttendance);
        absenceAttendanceRepository.save(absenceAttendance);

        if (files != null) {
            saveAttachementFiles(files, absenceAttendance);
        }

        return UpdateAbsenceAttendacneReponseDto.from(absenceAttendance);

    }

    private AbsenceAttendance getAbsenceAttendance(Long absenceAttendancesId) {
        return absenceAttendanceRepository.findById(absenceAttendancesId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유고 결석 신청 내역이 존재하지 않습니다."));
    }

    private void markAttachementFilesForDeletion(AbsenceAttendance absenceAttendance) {
        List<AbsenceAttendanceAttachmentFile> attachmentFiles = absenceAttendance.getActiveFiles();
        for (AbsenceAttendanceAttachmentFile attachmentFile : attachmentFiles) {
            attachmentFile.markDeletionRequested();
            absenceAttendanceAttachmentFileRepository.save(attachmentFile);
        }
    }

    public AbsenceAttendanceResponseDto getAbsenceAttendance(Member member, Long courseId, Long absenceAttendancesId) {

        AbsenceAttendance absenceAttendance = getAbsenceAttendance(absenceAttendancesId);

        Member student = memberRepository.findByStudent_Id(absenceAttendance.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생이 존재하지 않습니다."));

        Role role = member.getRole();
        if (role == Role.STUDENT) {
            log.info("권한 체크 학생");
            validateMatchApplicant(member, absenceAttendance);
        } else if (role == Role.MIDDLE_ADMIN) {
            log.info("권한 체크 중간 관리자");
            validateStaffManageCourse(member, courseId);
            //TODO 수강생이 해당 course에 속하는지
        }

        List<AbsenceAttendanceAttachmentFile> attachmentFiles = absenceAttendanceAttachmentFileRepository.findByActivateFilesById(absenceAttendance);
        List<AttachmentFileReposeDto> fileReposeDtoList = attachmentFiles.stream()
                .map(AttachmentFileReposeDto::from)
                .toList();

        return AbsenceAttendanceResponseDto.from(absenceAttendance, student, fileReposeDtoList);
    }

    private void validateStaffManageCourse(Member member, Long courseId) {
        boolean isCorrect = staffCourseRepository.existsByStaffIdAndCourseId(member.getStaff().getId(), courseId);
        if (!isCorrect) {
            throw new ForbiddenException("관리자가 관리하는 교육 과정에 대해서만 조회 가능합니다.");
        }
    }

    public Page<MyAbsenceAttendanceResponseDto> getMyAbsenceAttendances(Member member, Long courseId, Pageable pageable) {

        validateRegistrationCourse(member, courseId);

        return absenceAttendanceRepository.findByStudentIdAndCourseId(member.getStudentId(), courseId, pageable);

    }
}
