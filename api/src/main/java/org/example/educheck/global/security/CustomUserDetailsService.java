package org.example.educheck.global.security;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        return memberRepository.findByEmail(email)
//                .orElseThrow(() -> new EmailNotFoundException("사용자를 찾을 수 없습니다.")); // TODO: Exception
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
