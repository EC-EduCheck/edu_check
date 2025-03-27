package org.example.educheck.domain.absenceattendanceattachmentfile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAttachmentFileReposeDto {

    private Long id;
    private String originalName;
    private String fileUrl;
    private String mine;

}
