package org.example.educheck.domain.meetingroomreservation.service;
/**
 * 1. 모든 외부 의존성 (repo) 는 Mock
 * 2. 입력값, 예상 결과값 설정
 * 3. 실제 메서드 호출
 * 4. 결과 검증
 */

import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.awaitility.Awaitility.given;

@ExtendWith(SpringExtension.class)
class MeetingRoomReservationServiceTest {

    @Mock
    private MeetingRoomReservationRepository meetingRoomReservationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    private MeetingRoomReservationService meetingRoomReservationService;

    //사용되는 변수들
    private Long campusId;
    private MeetingRoom meetingRoom;
    private Member member;
    private MeetingRoomReservationRequestDto requestDto;
    private User mockUser;


    @BeforeEach
    void setUp() {

        mockUser = (User) User.builder()
                .username("test@naver.com")
                .password("password!")
                .roles("ROLE_STUDENT")
                .build();


        //findByUsername()시, Mocking 유저를 반환하도록 처리
        given(memberRepository.findByEmail("test@naver.com")).willReturn(Optional.of(mockUser));
    }

    @Test
    void createReservation() {
    }
}