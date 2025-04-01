package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.dto.response.MyProfileResponseDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyService {

    private final MemberRepository memberRepository;


    public MyProfileResponseDto getMyProfile(Member member) {
        return memberRepository.findMyProfileDtoById(member.getId());
    }
}
