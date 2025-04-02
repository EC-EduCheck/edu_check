package org.example.educheck.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.registration.entity.RegistrationStatus;

@Builder
@Getter
@AllArgsConstructor
public class RegisteredMemberResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private RegistrationStatus status;

    public static RegisteredMemberResponseDto from(Member member, RegistrationStatus status) {
        return RegisteredMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .phone(member.getPhoneNumber())
                .email(member.getEmail())
                .status(status)
                .build();
    }

}
