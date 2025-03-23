package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.dto.response.AttendanceRateDto;
import org.example.educheck.domain.member.dto.response.AttendanceRateProjection;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

    private final MemberService memberService;
    private final LectureRepository lectureRepository;

    public AttendanceRateDto getMyAttendanceRate(Long courseId) {

        AttendanceRateProjection projection = lectureRepository.findAttendanceRateByCourse(courseId, 12L);
        return AttendanceRateDto.from(projection);

    }

}
