import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { reservationApi } from '../../api/reservationApi';
import Modal from '../../components/modal/Modal';
import moment from 'moment';
import 'moment/locale/ko';
import { Calendar, momentLocalizer, Views } from 'react-big-calendar';

import styles from './RoomReservation.module.css';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './calendar.css';
import dayjs from 'dayjs';

// 한국어 로케일 설정
moment.locale('ko');
const localizer = momentLocalizer(moment);

const RoomReservation = () => {
  const [resources, setResources] = useState([]);
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [loading, setLoading] = useState(true);
  const [isOpen, setIsOpen] = useState(false);
  const [modalData, setModalData] = useState({});
  const campusId = useSelector((state) => state.auth.user.campusId);
  const courseId = useSelector((state) => state.auth.user.courseId);
  const memberId = useSelector((state) => state.auth.user.memberId);

  useEffect(() => {
    if (!campusId) return;

    const fetchData = async () => {
      try {
        const formattedDate = moment(selectedDate).format('YYYY-MM-DD');
        const response = await reservationApi.getReservations(campusId, formattedDate);

        const meetingRooms = response.data.data.meetingRooms;

        const resourcesData = meetingRooms.map((room) => ({
          id: room.meetingRoomId.toString(),
          title: room.meetingRoomName,
        }));

        setResources(resourcesData);

        const eventsData = meetingRooms.flatMap((room) =>
          room.reservations.map((reservation) => ({
            id: `${reservation.meetingRoomReservationId}`,
            resourceId: room.meetingRoomId.toString(),
            title: `${reservation.reserverName} 예약`,
            start: new Date(reservation.startDateTime.replace(' ', 'T')),
            end: new Date(reservation.endDateTime.replace(' ', 'T')),
            reserverId: reservation.reserverId,
            reserverName: reservation.reserverName,
          })),
        );

        setEvents(eventsData);
      } catch (error) {
        console.error('데이터 불러오기 실패:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [campusId, selectedDate]);

  const handleNavigate = (date) => {
    setSelectedDate(date);
  };

  const isWithinOperationHours = (start, end) => {
    const startHour = start.getHours();
    const endHour = end.getHours();
    const endMinutes = end.getMinutes();

    return !(startHour < 9 || (endHour === 22 && endMinutes > 0) || endHour > 22);
  };

  const handleSelectSlot = async ({ start, end, resourceId }) => {
    const now = new Date();
    const today = new Date().setHours(0, 0, 0, 0);

    if (start < today) {
      alert('당일 예약만 가능합니다.');
      return;
    }

    if (start < now) {
      alert('현재 시간 이후로만 예약할 수 있습니다.');
      return;
    }

    if (!isWithinOperationHours(start, end)) {
      alert('운영 시간(09:00 - 22:00) 내에서만 예약할 수 있습니다.');
      return;
    }

    if (!window.confirm('이 시간에 예약하시겠습니까?')) return;

    const convertToKST = (date) => {
      return new Date(date.getTime() + 9 * 60 * 60 * 1000);
    };

    const requestBody = {
      startTime: convertToKST(new Date(start)).toISOString(),
      endTime: convertToKST(new Date(end)).toISOString(),
      meetingRoomId: resourceId,
      courseId: courseId,
    };

    try {
      const response = await reservationApi.createReservation(campusId, requestBody);
      console.log(response);
      if (response.status === 201) {
        alert('예약이 완료되었습니다.');

        // 성공적으로 예약이 되었을 때 UI에 예약 추가
        console.log(response.data.data.reservationId);
        const newEvent = {
          id: response.data.data.reservationId,
          resourceId: resourceId,
          title: `${response.data.data.reserverName || '새 예약'} 예약`,
          start,
          end,
          reserverId: response.data.data.reserverId || Date.now(),
          reserverName: response.data.data.reserverName || '새 예약',
        };

        setEvents((prevEvents) => [...prevEvents, newEvent]);
      }
    } catch (error) {
      console.error(error);
      const errorMessage = error.response?.data?.message ?? '오류가 발생했습니다.';
      alert(errorMessage);
    }
  };

  const cancelReservation = async (eventId, event) => {
    setIsOpen(false);
    console.log(eventId);

    try {
      const response = await reservationApi.cancelReservation(campusId, eventId);

      if (response.status === 200 || response.status === 204) {
        alert('예약이 취소되었습니다.');
        setEvents(events.filter((e) => e.id !== eventId));
      }
    } catch (error) {
      console.error('예약 취소 중 오류', error);
      alert('예약 취소에 실패했습니다.');
    }
  };

  const handleSelectEvent = async (event) => {
    const resourceTitle = resources.find((r) => r.id === event.resourceId)?.title;

    const formatTime = (date) => {
      return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    };

    const confirmContent = (
      <>
        <h2 className="subTitle">예약 정보</h2>
        <ul className={styles.modal}>
          <li>
            <p>예약자 : </p>
            <p>{event.reserverName}</p>
          </li>
          <li>
            <p>장소 : </p>
            <p>{resourceTitle}</p>
          </li>
          <li>
            <p>시작 시간 : </p>
            <p>{formatTime(event.start)}</p>
          </li>
          <li>
            <p>종료 시간 : </p>
            <p>{formatTime(event.end)}</p>
          </li>
        </ul>
      </>
    );

    setModalData({
      content: confirmContent,
      mainClick:
        memberId === event.reserverId && dayjs().isBefore(dayjs(event.end))
          ? () => cancelReservation(event.id)
          : null,
      mainText: memberId === event.reserverId && dayjs().isBefore(dayjs(event.end)) ? '삭제' : '',
    });

    setIsOpen(true);
  };

  const eventPropGetter = (event) => {
    const colors = ['#d1f2a2', '#c8f5b3', '#f5a3a6', '#ffd48a', '#e8fbd9', '#fde5d2', '#fff7e8'];
    const colorIndex = Math.floor(Math.random() * colors.length);
    // event.resourceId;
    return {
      style: {
        backgroundColor: colors[colorIndex],
      },
    };
  };

  if (loading) {
    return <div>데이터를 불러오는 중...</div>;
  }

  // 캘린더 툴바 컴포넌트
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
          </span>
        </div>
        <span className="rbc-toolbar">
          <span className="rbc-toolbar-label">
            <div className={styles.infoIconBox}>
              <div className={styles.icon}>
                <img src="/assets/clock-icon.png" alt="시계 아이콘" />
              </div>
              <p>
                09:00 - 22:00 <span>(15분 단위 당일 예약만 가능)</span>
              </p>
            </div>
          </span>
        </span>
      </>
    );
  };

  return (
    <>
      <Modal isOpen={isOpen} onClose={() => setIsOpen(false)} {...modalData}></Modal>
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
            min={
              new Date(
                selectedDate.getFullYear(),
                selectedDate.getMonth(),
                selectedDate.getDate(),
                9,
                0,
                0,
              )
            }
            max={
              new Date(
                selectedDate.getFullYear(),
                selectedDate.getMonth(),
                selectedDate.getDate(),
                22,
                0,
                0,
              )
            }
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
    </>
  );
};

export default RoomReservation;
