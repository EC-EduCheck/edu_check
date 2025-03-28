import React, { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

const DUMMY_RESERVATIONS = [
  {
    id: '1',
    title: '회의실 A',
    start: '2024-03-28T10:00:00',
    end: '2024-03-28T12:00:00',
    backgroundColor: '#4CAF50', // 녹색
    borderColor: '#45a049',
  },
  {
    id: '2',
    title: '세미나실 B',
    start: '2024-03-29T14:00:00',
    end: '2024-03-29T16:00:00',
    backgroundColor: '#2196F3', // 파란색
    borderColor: '#1976D2',
  },
  {
    id: '3',
    title: '교육장 C',
    start: '2024-03-30T09:00:00',
    end: '2024-03-30T11:00:00',
    backgroundColor: '#FF9800', // 주황색
    borderColor: '#F57C00',
  },
];

const ReservationCalendar = () => {
  const [events, setEvents] = useState(DUMMY_RESERVATIONS);

  // 날짜 클릭 핸들러
  const handleDateClick = (selectInfo) => {
    const title = prompt('새 예약 이름을 입력하세요:');
    if (title) {
      const newEvent = {
        id: String(events.length + 1),
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        backgroundColor: '#9C27B0', // 보라색
        borderColor: '#7B1FA2',
      };

      setEvents([...events, newEvent]);
    }
  };

  // 이벤트 클릭 핸들러
  const handleEventClick = (clickInfo) => {
    const confirmed = window.confirm(`
      ${clickInfo.event.title} 예약 정보
      시작: ${clickInfo.event.startStr}
      종료: ${clickInfo.event.endStr}
      
      삭제하시겠습니까?
    `);

    if (confirmed) {
      // 이벤트 삭제
      clickInfo.event.remove();
    }
  };

  return (
    <div className="p-4">
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        headerToolbar={{
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay',
        }}
        events={events}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        editable={true}
        selectable={true}
      />
    </div>
  );
};

export default ReservationCalendar;
