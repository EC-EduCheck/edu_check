package org.example.educheck.domain.absenceattendanceattachmentfile.repository;

import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AbsenceAttendanceAttachmentFileRepository extends JpaRepository<AbsenceAttendanceAttachmentFile, Long> {

    @Query("SELECT a FROM absence_attendacne_attachment_file  a WHERE a.deletionRequestedAt <= :date")
    List<AbsenceAttendanceAttachmentFile> findByDeletionRequestedAtBeforeAndDeleted(LocalDateTime date);

}
