package org.example.educheck.domain.consultingattachmentfile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.consulting.entity.Consulting;

@Getter
@Entity(name = "consulting_attachment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingAttachmentAttachmentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;

    private String url;
    private String mine;
    private String originalName;
    private String s3Key;

}
