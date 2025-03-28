package org.example.educheck.global.common.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.example.educheck.domain.absenceattendanceattachmentfile.repository.AbsenceAttendanceAttachmentFileRepository;
import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.ServerErrorException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileCleanupScheduler {

    private final S3Service s3Service;
    private final AbsenceAttendanceAttachmentFileRepository absenceAttendanceAttachmentFileRepository;

    @Scheduled(cron = "0 0 3 ? * MON")
    public void deleteExpireFiles() {
        LocalDateTime threeMonthAgo = LocalDateTime.now().minusMonths(3);

        List<AbsenceAttendanceAttachmentFile> expiredFiles = absenceAttendanceAttachmentFileRepository.findByDeletionRequestedAtBeforeAndDeleted(threeMonthAgo);

        log.info("Expired files count: {}", expiredFiles.size());


        for (AbsenceAttendanceAttachmentFile expiredFile : expiredFiles) {
            try {
                String s3Key = expiredFile.getS3Key();
                s3Service.deleteFile(s3Key);
                absenceAttendanceAttachmentFileRepository.delete(expiredFile);
            } catch (S3Exception e) {
                log.error(e.getMessage());
                throw new ServerErrorException(ErrorCode.FILE_DELETE_ERROR);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new ServerErrorException();
            }
        }
    }

}
