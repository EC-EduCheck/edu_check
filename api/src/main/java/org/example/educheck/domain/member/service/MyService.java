package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.dto.response.AttendanceRateDto;
import org.example.educheck.domain.member.dto.response.AttendanceRateProjection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

    private final MemberService memberService;
    private final LectureRepository lectureRepository;

    public void getMyAttendanceRate(UserDetails userDetails, Long courseId) {
//        Member member = memberService.getMemberByUserDetails(userDetails);
//        Object dto = lectureRepository.findAttendanceRateByCourse(courseId, member.getId());
        AttendanceRateProjection projection = lectureRepository.findAttendanceRateByCourse(courseId, 1L);
        log.info("출석률 : {}", projection.getAttendanceRate());
        AttendanceRateDto dto = AttendanceRateDto.from(projection);
        log.info(dto.toString());

    }

}
