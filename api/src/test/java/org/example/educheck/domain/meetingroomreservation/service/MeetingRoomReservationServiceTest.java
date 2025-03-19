package org.example.educheck.domain.meetingroomreservation.service;
/**
 * 1. 모든 외부 의존성 (repo) 는 Mock
 * 2. 입력값, 예상 결과값 설정
 * 3. 실제 메서드 호출
 * 4. 결과 검증
 */

import org.example.educheck.domain.campus.Campus;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.entity.Role;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MeetingRoomReservationServiceTest {

    @InjectMocks
    private MeetingRoomReservationService meetingRoomReservationService;

    @Mock
    private MeetingRoomReservationRepository meetingRoomReservationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @Mock // dto오 Mocking 처리가 가능한가?
    private MeetingRoomReservationRequestDto requestDto;


    //사용되는 변수들
    private UserDetails mockUser;
    private Member mockMember;
    private MeetingRoom mockMeetingRoom;
    private Campus mockCampus;

    private Long campusId = 1L;


    @BeforeEach
    void setUp() {

        //미리 준비된 회의실, 켐퍼스, 사용자
        String testEmail = "test@naver.com";
        String testPassword = "password!";


        mockMember = Member.builder()
                .email(testEmail)
                .password(testPassword)
                .build();

        mockCampus = Campus.builder()
                .name("성동 캠퍼스")
                .build();
        ReflectionTestUtils.setField(mockCampus, "id", 1L);

        mockMeetingRoom = MeetingRoom.builder()
                .name("Room 1")
                .campus(mockCampus)
                .build();
        //builder 패턴을 깨지 않으면서 mock데이터를 만들면서 pk값을 부여하고 싶어
        ReflectionTestUtils.setField(mockMeetingRoom, "id", 1L);


        //인자로 주어지는 UserDetails 준비
        mockUser = User.builder()
                .username(testEmail)
                .password(testPassword)
                .roles(String.valueOf(Role.STUDENT))
                .build();

        //Mock 설정: memberRepo에서 특정 이메일 조회시 mockMember 반환하도록 설정
        Mockito.when(memberRepository.findByEmail(mockMember.getEmail()))
                .thenReturn(Optional.of(mockMember));

        //Mock 설정: meetingRoomRepo에서 특정 id의 회의실 조회시 mockMeetingRoom반환하도록 설정
        Mockito.when(meetingRoomRepository.findById(mockMeetingRoom.getId()))
                .thenReturn(Optional.of(mockMeetingRoom));

    }

    @Test
    void 예약_성공() {
        //내부에서만 쓰이는 dto Mock 설정
        //given
        given(requestDto.getMeetingRoomId()).willReturn(1L);
        given(requestDto.getStartTime()).willReturn(LocalDateTime.now());
        given(requestDto.getEndTime()).willReturn(LocalDateTime.now().plusHours(1));
        given(requestDto.toEntity(any(), any())).willReturn(new MeetingRoomReservation(mockMember, mockMeetingRoom, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        //when
        meetingRoomReservationService.createReservation(mockUser, campusId, requestDto);

        //then
        verify(meetingRoomReservationRepository).save(any(MeetingRoomReservation.class)); // 예약이 정상적으로 저장됐는지 확인

    }

    @Test
    void 예약_실패_중복() {
        //given

        //when
        //then
    }
}