import React, { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';

const DUMMY_RESERVATIONS = [
  {
    id: '1',
    title: '회의실 A',
    start: '2024-03-29T09:00:00',
    end: '2024-03-29T10:30:00',
    backgroundColor: '#4CAF50',
    borderColor: '#45a049',
  },
  {
    id: '2',
    title: '세미나실 B',
    start: '2024-03-29T13:00:00',
    end: '2024-03-29T14:30:00',
    backgroundColor: '#2196F3',
    borderColor: '#1976D2',
  },
  {
    id: '3',
    title: '교육장 C',
    start: '2024-03-29T16:00:00',
    end: '2024-03-29T17:00:00',
    backgroundColor: '#FF9800',
    borderColor: '#F57C00',
  },
];

const ReservationDayTimeline = () => {
  const [events, setEvents] = useState(DUMMY_RESERVATIONS);
  const [selectedDate, setSelectedDate] = useState(new Date());

  // 날짜 선택 시 처리
  const handleDateSet = (dateInfo) => {
    setSelectedDate(dateInfo.view.currentStart);
  };

  // 타임슬롯 클릭 핸들러
  const handleTimeSlotSelect = (selectInfo) => {
    const title = prompt('새 예약 이름을 입력하세요:');
    if (title) {
      const newEvent = {
        id: String(events.length + 1),
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        backgroundColor: '#9C27B0',
        borderColor: '#7B1FA2',
      };

      setEvents([...events, newEvent]);
    }
  };

  // 이벤트 클릭 핸들러
  const handleEventClick = (clickInfo) => {
    const confirmed = window.confirm(`
      ${clickInfo.event.title} 예약 정보
      시작: ${new Date(clickInfo.event.start).toLocaleTimeString()}
      종료: ${new Date(clickInfo.event.end).toLocaleTimeString()}
      
      삭제하시겠습니까?
    `);

    if (confirmed) {
      setEvents(events.filter((event) => event.id !== clickInfo.event.id));
    }
  };

  return (
    <div className="p-4">
      <div className="mb-4">
        <h2 className="text-xl font-bold">{selectedDate.toLocaleDateString()} 예약 타임라인</h2>
        <button
          className="bg-blue-500 text-white px-3 py-1 rounded mt-2"
          onClick={() => setSelectedDate(new Date())}
        >
          오늘로 이동
        </button>
      </div>

      <FullCalendar
        plugins={[timeGridPlugin, interactionPlugin]}
        initialView="timeGridDay"
        headerToolbar={{
          left: 'prev,next',
          center: 'title',
          right: '',
        }}
        initialDate={selectedDate}
        events={events}
        slotMinTime="08:00:00"
        slotMaxTime="22:00:00"
        allDaySlot={false}
        selectable={true}
        selectMirror={true}
        select={handleTimeSlotSelect}
        eventClick={handleEventClick}
        datesSet={handleDateSet}
        height="auto"
      />
    </div>
  );
};

export default ReservationDayTimeline;
