package org.example.educheck.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyProfileResponseDto {

    private Long id;
    private String phoneNumber;
    private LocalDate birthDate;

}
