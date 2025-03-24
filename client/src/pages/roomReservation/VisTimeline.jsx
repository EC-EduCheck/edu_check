import React, { useEffect, useRef } from 'react'
import { DataSet } from 'vis-data'
import { Timeline } from 'vis-timeline'




export default function VisTimeline() {
  const timelineRef = useRef(null);

  useEffect(() => {
    // 타임라인 데이터
    const items = new DataSet([
      { id: 1, content: "회의 A", start: "2025-03-24T10:00:00", end: "2025-03-24T11:00:00" },
      { id: 2, content: "회의 B", start: "2025-03-24T13:00:00", end: "2025-03-24T14:30:00" },
      { id: 3, content: "회의 C", start: "2025-03-24T16:00:00", end: "2025-03-24T17:00:00" },
    ]);

    // 타임라인 옵션
    const options = {
      start: "2025-03-24T09:00:00",
      end: "2025-03-24T22:00:00",
      min: "2025-03-24T09:00:00",
      max: "2025-03-24T22:00:00",
      showCurrentTime: true,
      stack: false, // 개별 라인에 표시 (true: 겹쳐서 정리)
    };

    // 타임라인 초기화
    const timeline = new Timeline(timelineRef.current, items, options);

    return () => timeline.destroy(); // 컴포넌트 언마운트 시 타임라인 해제
  }, []);

  return (
    <div ref={timelineRef} style={{ width: "100%", height: "300px", border: "1px solid gray" }} />
  );
}
