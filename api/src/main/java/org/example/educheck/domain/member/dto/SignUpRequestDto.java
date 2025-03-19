package org.example.educheck.domain.member.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.entity.Role;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Length(min = 2, max = 50, message = "이름은 2개 자리 이상 50개 자리 이하로 입력할 수 있습니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @NotNull(message = "생년날짜는 필수 입력값입니다.")
    @PastOrPresent(message = "생년월일은 오늘 날짜를 초과할 수 없습니다.")
    private LocalDate birthDate;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull(message = "강좌id는 필수 입력값입니다.")
    private Long courseId;

    public Member toEntity(String encodedPassword) {

        return Member.builder()
                .name(name)
                .phoneNumber(phone)
                .birthDate(birthDate)
                .email(email)
                .password(encodedPassword)
                .role(Role.ROLE_STUDENT)
                .build();
    }

    public String getFormattedBirthDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        return birthDate.format(formatter);
    }
}
