import React, { useEffect, useRef } from 'react';
import { DataSet, Timeline } from 'vis-timeline/standalone';
import 'vis-timeline/styles/vis-timeline-graph2d.min.css';

const TimelineComponent = () => {
  const timelineRef = useRef(null);

  // 시간을 15분 간격으로 나누기
  const splitBy15Minutes = (startTime, endTime) => {
    const timeSlots = [];
    let currentTime = new Date(startTime);

    //endTime보다 작은 동안 15분씩 증가
    while (currentTime < endTime) {
      let nextTime = new Date(currentTime.getTime() + 15 * 60 * 1000); // 15분씩 증가
      // start : 09:00  end:09:15 -> current : 09:15 이 형태로 반복!
      timeSlots.push({
        start: new Date(currentTime), // 현재 시간 (복사)
        end: new Date(nextTime), // 15분 후
      });
      currentTime = nextTime;
    }
    return timeSlots;
  };

  useEffect(() => {
    if (!timelineRef.current) return;

    const currentDate = new Date();

    // 오전 9시로 start 설정
    const start = new Date(currentDate);
    start.setHours(9, 0, 0, 0);

    // 오후 10시로 end 설정
    const end = new Date(currentDate);
    end.setHours(22, 0, 0, 0); // 종료 시간: 오후 10시

    // 타임라인 옵션
    const options = {
      //타임라인 아이템들이 겹치지 않고 평행하게 표시
      start: start,
      end: end,
      editable: true,
      margin: {
        item: 10,
        axis: 5,
      },
      min: start,
      max: end,
      orientation: 'top',
    };

    // 예시 예약 시간 (예시: 예약 1은 오전 9시 30분부터 오후 1시까지)
    const reservationStart = new Date(currentDate);
    reservationStart.setHours(9, 30, 0, 0); // 시작 시간: 오전 9시 30분
    const reservationEnd = new Date(currentDate);
    reservationEnd.setHours(13, 0, 0, 0); // 종료 시간: 오후 1시

    // 15분 간격으로 나누기
    const timeSlots = splitBy15Minutes(reservationStart, reservationEnd);

    // 아이템 생성
    const items = new DataSet();
    let order = 1;
    let track = 1;

    timeSlots.map((timeSlot) => {
      items.add({
        id: order,
        group: 1,
        start: timeSlot.start,
        end: timeSlot.end,
        content: `Order ${order}`,
      });
      order++;
    });

    const meetingRooId = {
      1: { id: 1, content: '트랙 1' },

      2: { id: 2, content: '1층 회의실 2' },
    };
    // 그룹 생성
    const groups = new DataSet([
      { id: 1, content: '트랙 1' },
      { id: 2, content: '트랙 2' },
      { id: 3, content: '트랙 3' },
      { id: 4, content: '트랙 4' },
    ]);

    // // 타임라인 생성
    const timeline = new Timeline(timelineRef.current);
    timeline.setGroups(groups);
    timeline.setItems(items);
    timeline.setOptions(options);

    return () => {
      timeline.destroy(); // 컴포넌트 언마운트 시 타임라인 정리
    };
  }, []);
  return <div ref={timelineRef} style={{ height: '400px' }} />;
};

export default TimelineComponent;
