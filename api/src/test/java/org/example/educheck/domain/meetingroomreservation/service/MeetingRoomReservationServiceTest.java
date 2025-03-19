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
import org.example.educheck.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MeetingRoomReservationServiceTest {

    private static UserDetails mockUser;
    private static Member mockMember;
    private static MeetingRoom mockMeetingRoom;
    private static Campus mockCampus;
    private static Long campusId = 1L;
    private static String testEmail = "user1@naver.com";
    private static String testPassword = "password!";
    private static MeetingRoomReservation existingMeetingRoomReservation;
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


    @BeforeAll
    static void commonSetUp() {

        mockUser = User.builder()
                .username(testEmail)
                .password(testPassword)
                .build();

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

        ReflectionTestUtils.setField(mockMeetingRoom, "id", 1L);

    }

    @BeforeEach
    void setUp() {

        existingMeetingRoomReservation = MeetingRoomReservation.builder()
                .member(mockMember)
                .meetingRoom(mockMeetingRoom)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        Mockito.when(memberRepository.findByEmail(mockMember.getEmail()))
                .thenReturn(Optional.of(mockMember));

        Mockito.when(meetingRoomRepository.findById(mockMeetingRoom.getId()))
                .thenReturn(Optional.of(mockMeetingRoom));


    }

    @Test
    void 예약_성공() {

        Mockito.when(meetingRoomReservationRepository.existsOverlappingReservation(any(MeetingRoom.class), any(LocalDate.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(false);


        given(requestDto.getMeetingRoomId()).willReturn(1L);
        given(requestDto.getStartTime()).willReturn(LocalDateTime.of(2025, 3, 19, 9, 0));
        given(requestDto.getEndTime()).willReturn(LocalDateTime.of(2025, 3, 19, 10, 0));
        given(requestDto.toEntity(any(), any())).willReturn(new MeetingRoomReservation(mockMember, mockMeetingRoom, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));

        //when
        meetingRoomReservationService.createReservation(mockUser, campusId, requestDto);

        //then
        verify(meetingRoomReservationRepository).save(any(MeetingRoomReservation.class));

    }

    @Test
    void 예약_실패_중복() {

        Mockito.when(meetingRoomReservationRepository.existsOverlappingReservation(any(MeetingRoom.class), any(LocalDate.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);


        LocalDateTime duplicatedStartTime = LocalDateTime.now().plusMinutes(30);
        LocalDateTime duplicatedEndTime = LocalDateTime.now().plusHours(1);

        given(requestDto.getMeetingRoomId()).willReturn(1L);
        given(requestDto.getStartTime()).willReturn(duplicatedStartTime);
        given(requestDto.getEndTime()).willReturn(duplicatedEndTime);

        Mockito.when(meetingRoomReservationRepository.existsOverlappingReservation(
                        any(MeetingRoom.class), any(LocalDate.class), any(LocalDateTime.class), any(LocalDateTime.class)
                ))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> meetingRoomReservationService.createReservation(mockUser, campusId, requestDto));

        verify(meetingRoomReservationRepository, never())
                .save(any(MeetingRoomReservation.class));

    }
}