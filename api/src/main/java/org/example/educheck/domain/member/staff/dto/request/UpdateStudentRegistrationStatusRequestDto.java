package org.example.educheck.domain.member.staff.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.registration.entity.RegistrationStatus;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStudentRegistrationStatusRequestDto {

    private RegistrationStatus status;

}
