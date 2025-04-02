package org.example.educheck.domain.member.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMyProfileRequestDto {
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    public String phoneNumber;
    @PastOrPresent(message = "생년월일은 오늘 날짜를 초과할 수 없습니다.")
    public LocalDate birthDate;

    public String currentPassword;
    public String newPassword;
}
