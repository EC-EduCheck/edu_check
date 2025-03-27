package org.example.educheck.domain.absenceattendanceattachmentfile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.absenceattendanceattachmentfile.entity.AbsenceAttendanceAttachmentFile;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentFileReposeDto {

    private Long fileId;
    private String originalName;
    private String fileUrl;
    private String mine;

    public static AttachmentFileReposeDto from(AbsenceAttendanceAttachmentFile file) {
        return AttachmentFileReposeDto.builder()
                .fileId(file.getId())
                .originalName(file.getOriginalName())
                .fileUrl(file.getUrl())
                .mine(file.getMime())
                .build();

    }

}
