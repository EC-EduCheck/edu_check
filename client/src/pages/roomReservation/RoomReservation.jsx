import React, { useState, useEffect } from 'react';
import styles from './RoomReservation.module.css';
import { Calendar, momentLocalizer, Views } from 'react-big-calendar';
import moment from 'moment';
import 'moment/locale/ko';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useSelector } from 'react-redux';
import { reservationApi } from '../../api/reservationApi';
import './calendar.css';

// 한국어 로케일 설정
moment.locale('ko');
const localizer = momentLocalizer(moment);

const RoomReservation = () => {
  const [resources, setResources] = useState([]);
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [loading, setLoading] = useState(true);
  const campusId = useSelector((state) => state.auth.user.campusId);
  const courseId = useSelector((state) => state.auth.user.courseId);

  useEffect(() => {
    if (!campusId) return;

    const fetchData = async () => {
      try {
        const response = await reservationApi.getReservations(campusId);
        const meetingRooms = response.data.data.meetingRooms;

        // 회의실 데이터 변환
        const resourcesData = meetingRooms.map((room) => ({
          id: room.meetingRoomId.toString(),
          title: room.meetingRoomName,
        }));

        setResources(resourcesData);

        // 이벤트(예약) 데이터 변환
        const eventsData = meetingRooms.flatMap((room) =>
          room.reservations.map((reservation) => ({
            id: `${room.meetingRoomId}-${reservation.reserverId}`,
            resourceId: room.meetingRoomId.toString(),
            title: `${reservation.reserverName} 예약`,
            start: new Date(reservation.startDateTime.replace(' ', 'T')),
            end: new Date(reservation.endDateTime.replace(' ', 'T')),
            reserverId: reservation.reserverId,
            reserverName: reservation.reserverName,
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

  const handleNavigate = (date) => {
    setSelectedDate(date);
  };

  const handleSelectSlot = async ({ start, end, resourceId }) => {
    // 운영시간 체크 (9:00 - 22:00)
    const startHour = start.getHours();
    const endHour = end.getHours();
    const endMinutes = end.getMinutes();

    if (startHour < 9 || (endHour === 22 && endMinutes > 0) || endHour > 22) {
      alert('운영 시간(09:00 - 22:00) 내에서만 예약할 수 있습니다.');
      return;
    }

    const confirmed = window.confirm('이 시간에 예약하시겠습니까?');
    if (!confirmed) return;

    try {
      const formatISOString = (date) => {
        return date.toISOString().split('.')[0].replace('T', ' ');
      };

      const requestBody = {
        startTime: formatISOString(start),
        endTime: formatISOString(end),
        meetingRoomId: resourceId,
        courseId: courseId,
      };

      const response = await reservationApi.createReservation(campusId, requestBody);

      if (response.status === 201) {
        alert('예약이 완료되었습니다.');

        // 성공적으로 예약이 되었을 때 UI에 예약 추가
        const newEvent = {
          id: `new-${Date.now()}`,
          resourceId: resourceId,
          title: `${response.data.data.reserverName || '새 예약'} 예약`,
          start,
          end,
          reserverId: response.data.data.reserverId || Date.now(),
          reserverName: response.data.data.reserverName || '새 예약',
        };

        setEvents([...events, newEvent]);
      }
    } catch (error) {
      console.error('예약 중 오류', error);
      alert('예약 생성에 실패했습니다.');
    }
  };

  const handleSelectEvent = async (event) => {
    const resourceTitle = resources.find((r) => r.id === event.resourceId)?.title;

    const formatTime = (date) => {
      return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    };

    const confirmed = window.confirm(`
      예약 정보:
      예약자: ${event.reserverName}
      장소: ${resourceTitle}
      시작: ${formatTime(event.start)}
      종료: ${formatTime(event.end)}

      삭제하시겠습니까?
    `);

    if (confirmed) {
      try {
        const response = await reservationApi.cancelReservation(campusId, event.id.split('-')[0]);

        if (response.status === 200 || response.status === 204) {
          alert('예약이 취소되었습니다.');
          setEvents(events.filter((e) => e.id !== event.id));
        }
      } catch (error) {
        console.error('예약 취소 중 오류', error);
        alert('예약 취소에 실패했습니다.');
      }
    }
  };

  const eventPropGetter = (event) => {
    const colors = ['#eef7e6', '#57dd79', '#FF9800', '#9C27B0', '#E91E63', '#795548', '#607D8B'];
    const colorIndex = event.resourceId.charCodeAt(0) % colors.length;

    return {
      style: {
        backgroundColor: colors[colorIndex],
      },
    };
  };

  if (loading) {
    return <div>데이터를 불러오는 중...</div>;
  }

  // 캘린더 컴포넌트
  const Toolbar = (props) => {
    const { date } = props;

    const navigate = (action) => {
      props.onNavigate(action);
    };

    const dayOfWeek = {
      0: '일',
      1: '월',
      2: '화',
      3: '수',
      4: '목',
      5: '금',
      6: '토',
    };

    return (
      <>
        <div className="rbc-toolbar">
          <span className="rbc-toolbar-label">
            <div className={styles.infoIconBox}>
              <div className={styles.icon}>
                <img src="/assets/calendar-v2-icon.png" alt="달력 아이콘" />
              </div>
              <p>{`${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ${dayOfWeek[date.getDay()]}요일`}</p>
            </div>
          </span>
          <span className="rbc-btn-group">
            <button type="button" onClick={navigate.bind(null, 'TODAY')}>
              오늘
            </button>
            <button type="button" onClick={navigate.bind(null, 'PREV')}>
              이전
            </button>
            <button type="button" onClick={navigate.bind(null, 'NEXT')}>
              다음
            </button>
          </span>
        </div>
        <span className="rbc-toolbar">
          <span className="rbc-toolbar-label">
            <div className={styles.infoIconBox}>
              <div className={styles.icon}>
                <img src="/assets/clock-icon.png" alt="시계 아이콘" />
              </div>
              <p>
                09:00 - 22:00 <span>(15분 단위 예약 가능)</span>
              </p>
            </div>
          </span>
        </span>
      </>
    );
  };

  return (
    <div className={styles.reservationContainer}>
      <div className={styles.info}>
        <h2 className="subTitle">회의실 예약 현황</h2>
      </div>

      <div className={styles.calendar}>
        <Calendar
          localizer={localizer}
          events={events}
          resources={resources}
          resourceIdAccessor="id"
          resourceTitleAccessor="title"
          defaultView={Views.DAY}
          views={['day', 'week']}
          step={15}
          timeslots={4}
          min={new Date(selectedDate.setHours(9, 0, 0))}
          max={new Date(selectedDate.setHours(22, 0, 0))}
          date={selectedDate}
          onNavigate={handleNavigate}
          selectable
          onSelectSlot={handleSelectSlot}
          onSelectEvent={handleSelectEvent}
          eventPropGetter={eventPropGetter}
          formats={{
            timeGutterFormat: (date) => moment(date).format('HH:mm'),
            dayHeaderFormat: (date) => moment(date).format('YYYY년 MM월 DD일 (ddd)'),
          }}
          components={{
            toolbar: Toolbar,
          }}
        />
      </div>
    </div>
  );
};

export default RoomReservation;
