package org.example.educheck.domain.absenceAttendance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "absence_attendacne_attachment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "absence_attendance_id")
    private AbsenceAttendance absenceAttendance;

    private String url;
    private String mime;
    private String originalName;
    private String s3Key;

}
