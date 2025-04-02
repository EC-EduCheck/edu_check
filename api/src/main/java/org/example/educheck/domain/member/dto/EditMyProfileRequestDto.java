package org.example.educheck.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditMyProfileRequestDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Length(min = 2, max = 50, message = "이름은 2개 자리 이상 50개 자리 이하로 입력할 수 있습니다.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @PastOrPresent(message = "생년월일은 오늘 날짜를 초과할 수 없습니다.")
    private LocalDate birthDate;

    @Length(min = 8, message = "비밀번호은 8개 자리 이상으로 입력할 수 있습니다.")
    private String password;

}
