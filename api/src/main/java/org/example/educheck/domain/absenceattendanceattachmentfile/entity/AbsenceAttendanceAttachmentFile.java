package org.example.educheck.domain.absenceattendanceattachmentfile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.global.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Entity(name = "absence_attendacne_attachment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbsenceAttendanceAttachmentFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "absence_attendance_id")
    private AbsenceAttendance absenceAttendance;

    @Column(columnDefinition = "TEXT")
    private String url;
    private String mime;
    private String originalName;
    //버킷 내 고유 식별자, 전체 경로 포함
    private String s3Key;

    private LocalDateTime deletionRequestedAt;

    @Builder
    public AbsenceAttendanceAttachmentFile(AbsenceAttendance absenceAttendance, String url, String originalName, String s3Key, String mime) {
        this.absenceAttendance = absenceAttendance;
        this.url = url;
        this.originalName = originalName;
        this.s3Key = s3Key;
        this.mime = mime;
    }

    public void markDeletionRequested() {
        this.deletionRequestedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "AbsenceAttendanceAttachmentFile{" +
                "id=" + id +
                ", absenceAttendance=" + absenceAttendance +
                ", url='" + url + '\'' +
                ", mime='" + mime + '\'' +
                ", originalName='" + originalName + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", deletionRequestedAt=" + deletionRequestedAt +
                '}';
    }
}
