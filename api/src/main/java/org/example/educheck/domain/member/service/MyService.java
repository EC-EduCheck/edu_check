package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.member.dto.UpdateMyProfileRequestDto;
import org.example.educheck.domain.member.dto.response.MyProfileResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public MyProfileResponseDto getMyProfile(Member member) {
        return memberRepository.findMyProfileDtoById(member.getId());
    }

    @Transactional
    public void updateMyProfile(Member member, UpdateMyProfileRequestDto requestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        member.getEmail(), requestDto.getCurrentPassword())
        );


        Optional.ofNullable(requestDto.getPhoneNumber()).ifPresent(member::setPhoneNumber);
        Optional.ofNullable(requestDto.getBirthDate()).ifPresent(member::setBirthDate);

        if (requestDto.getNewPassword() != null) {
            String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
            member.setPassword(encodedPassword);
            member.setLastPasswordChangeDateTime(LocalDateTime.now());


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            member.getEmail(), requestDto.getCurrentPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication); //TODO: 삭제?

        }
        memberRepository.save(member);
    }
}
