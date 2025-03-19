//package org.example.educheck.domain.meetingroomreservation.service;
///**
// * 1. 모든 외부 의존성 (repo) 는 Mock
// * 2. 입력값, 예상 결과값 설정
// * 3. 실제 메서드 호출
// * 4. 결과 검증
// */
//
//import org.example.educheck.domain.campus.Campus;
//import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
//import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
//import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
//import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
//import org.example.educheck.domain.member.entity.Member;
//import org.example.educheck.domain.member.repository.MemberRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.awaitility.Awaitility.given;
//
//@ExtendWith(SpringExtension.class)
//class MeetingRoomReservationServiceTest {
//
//    @InjectMocks
//    private MeetingRoomReservationService meetingRoomReservationService;
//
//    @Mock
//    private MeetingRoomReservationRepository meetingRoomReservationRepository;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private MeetingRoomRepository meetingRoomRepository;
//
//    @Mock // dto오 Mocking 처리가 가능한가?
//    private MeetingRoomReservationRequestDto requestDto;
//
//    //사용되는 변수들
//    private UserDetails mockUser;
//    private Member mockMember;
//    private MeetingRoom mockMeetingRoom;
//
//    private Long campusId = 1L;
//    private MeetingRoomReservationRequestDto requestDto;
//
//
//
//
//
//    @BeforeEach
//    void setUp() {
//
//        String testEmail = "test@naver.com";
//        String testPassword = "password!";
//        mockUser = User.builder()
//                .username(testEmail)
//                .password(testPassword)
//                .roles("ROLE_STUDENT")
//                .build();
//
//        mockMember = Member.builder()
//                .email(testEmail)
//                .password(testPassword)
//                .build();
//
//        mockMeetingRoom = MeetingRoom.builder()
//                .name("Room 1")
//                .campus(Campus.builder()
//                        .name("1번 회의실")
//                        .build())
//                .build();
//
//
//
//
//        //mocking 기본 동작
//        member = memberRepository.findByEmail(testEmail).orElseThrow(() -> new IllegalArgumentException("이메일명 오류"));
//        given(memberRepository.findByEmail(testEmail)).re
//    }
//
//    @Test
//    void createReservation() {
//    }
//}