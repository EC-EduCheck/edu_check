package org.example.educheck.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponseDto {
    private final String token;
}
