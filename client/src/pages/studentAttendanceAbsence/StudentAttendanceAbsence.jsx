import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import resourceTimeGridPlugin from '@fullcalendar/resource-timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { reservationApi } from '../../api/reservationApi';
import { useSelector } from 'react-redux';

const RoomReservation = () => {
  const [resources, setResources] = useState([]);
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [loading, setLoading] = useState(true);
  const campusId = useSelector((state) => state.auth.user.campusId);
  const courseId = useSelector((state) => state.auth.user.courseId);
  console.log(campusId);
  useEffect(() => {
    if (!campusId) return;

    const fetchData = async () => {
      try {
        const response = await reservationApi.getReservations(campusId);
        const meetingRooms = response.data.data.meetingRooms;

        // 회의실 데이터 변환
        const resourcesData = meetingRooms.map((room) => ({
          id: room.meetingRoomId.toString(),
          roomName: room.meetingRoomName,
        }));

        setResources(resourcesData);

        // 이벤트(예약) 데이터 변환
        const eventsData = meetingRooms.flatMap((room) =>
          room.reservations.map((reservation) => ({
            id: `${room.meetingRoomId}-${reservation.reserverId}`,
            resourceId: room.meetingRoomId.toString(),
            title: `${reservation.reserverName} 예약`,
            start: formatDateTime(reservation.startDateTime),
            end: formatDateTime(reservation.endDateTime),
            extendedProps: {
              reserverId: reservation.reserverId,
              reserverName: reservation.reserverName,
            },
            backgroundColor: getRandomColor(),
          })),
        );

        setEvents(eventsData);

        // API 응답에서 첫 예약 날짜를 가져와 초기 날짜로 설정
        if (eventsData.length > 0) {
          setSelectedDate(new Date(eventsData[0].start));
        }
      } catch (error) {
        console.error('데이터 불러오기 실패:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [campusId]);

  // 날짜 선택 시 처리
  const handleDateSet = (dateInfo) => {
    setSelectedDate(dateInfo.view.currentStart);
  };

  const formatToLocalDateTime = (isoString) => {
    return isoString.split('+')[0];
  };

  const handleTimeSlotSelect = async (selectInfo) => {
    const confirmed = window.confirm('이 시간에 예약하시겠습니까?');
    if (!confirmed) return;

    try {
      const requestBody = {
        startTime: formatToLocalDateTime(selectInfo.startStr),
        endTime: formatToLocalDateTime(selectInfo.endStr),
        meetingRoomId: selectInfo.resource.id,
        courseId: courseId,
      };

      console.log(selectInfo.startStr);
      console.log(selectInfo.endStr);

      const response = await reservationApi.createReservation(campusId, requestBody);
      console.log(response);

      if (response.status === 201) {
        alert('예약이 완료되었습니다.');
      }
    } catch (error) {
      console.error('예약 중 오류', error);
    }

    if (title) {
      const newEvent = {
        id: `new-${Date.now()}`,
        resourceId: selectInfo.resource.id,
        title: `${title} 예약`,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        extendedProps: {
          reserverId: Date.now(),
          reserverName: title,
        },
        backgroundColor: getRandomColor(),
      };

      setEvents([...events, newEvent]);
    }
  };

  // 이벤트 클릭 핸들러
  const handleEventClick = async (clickInfo) => {
    const event = clickInfo.event;
    const resourceId = event.getResources()[0].id;
    const resourceTitle = resources.find((r) => r.id === resourceId)?.title;
    const reserverName = event.extendedProps.reserverName;

    const confirmed = window.confirm(`
      예약 정보:
      예약자: ${reserverName}
      장소: ${resourceTitle}
      시작: ${formatDisplayTime(event.start)}
      종료: ${formatDisplayTime(event.end)}
      
      삭제하시겠습니까?
    `);

    if (confirmed) {
      setEvents(events.filter((e) => e.id !== event.id));
    }

    console.log(resourceId);
    const response = await reservationApi.cancelReservation(campusId, resourceId);
    console.log(response);
  };

  // 날짜 포맷 변환 함수 (API 형식 -> FullCalendar 형식)
  const formatDateTime = (dateTimeStr) => {
    return dateTimeStr.replace(' ', 'T');
  };

  // 화면 표시용 시간 포맷
  const formatDisplayTime = (date) => {
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  // 랜덤 색상 생성 함수
  const getRandomColor = () => {
    const colors = ['#4CAF50', '#2196F3', '#FF9800', '#9C27B0', '#E91E63', '#795548', '#607D8B'];
    return colors[Math.floor(Math.random() * colors.length)];
  };

  if (loading) {
    return <div className="p-4">데이터를 불러오는 중...</div>;
  }

  return (
    <div className="p-4">
      <div className="mb-4">
        <h2 className="text-xl font-bold">{selectedDate.toLocaleDateString()} 회의실 예약 현황</h2>
        <button
          className="bg-blue-500 text-white px-3 py-1 rounded mt-2 mr-2"
          onClick={() => setSelectedDate(new Date())}
        >
          오늘로 이동
        </button>
        <span className="text-sm text-gray-600">
          운영 시간: 09:00 - 22:00 (15분 단위로 예약 가능)
        </span>
      </div>

      <FullCalendar
        plugins={[resourceTimeGridPlugin, interactionPlugin]}
        initialView="resourceTimeGridDay"
        headerToolbar={{
          left: 'prev,next',
          center: 'title',
          right: '',
        }}
        resources={resources}
        initialDate={selectedDate}
        events={events}
        slotMinTime="09:00:00"
        slotMaxTime="22:00:00"
        slotDuration="00:15:00"
        slotLabelInterval="01:00:00"
        slotLabelFormat={{
          hour: '2-digit',
          minute: '2-digit',
          hour12: false,
        }}
        allDaySlot={false}
        selectable={true}
        selectMirror={true}
        select={handleTimeSlotSelect}
        eventClick={handleEventClick}
        datesSet={handleDateSet}
        snapDuration="00:15:00"
        height="auto"
      />
    </div>
  );
};

export default RoomReservation;
