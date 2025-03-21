package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.lecture.repository.LectureRepository;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MemberService memberService;
    private final LectureRepository lectureRepository;

    public void getMyAttendanceRate(UserDetails userDetails, Long courseId) {
        Member member = memberService.getMemberByUserDetails(userDetails);
        List<Object[]> attendanceRateByCourse = lectureRepository.findAttendanceRateByCourse(courseId, member.getId());
    }

}
