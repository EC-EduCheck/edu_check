import React, { useState, useEffect } from 'react';
import { Calendar as BigCalendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import styles from './calendar.module.css';
import { fetchHolidays } from '../../api/holidayApi';

const localizer = momentLocalizer(moment);

export default function Calendar({ attendanceData }) {
  const [holidays, setHolidays] = useState([]);
  const [currentDate, setCurrentDate] = useState(new Date());
  const [view, setView] = useState('month');

  // 공휴일 데이터 가져오기
  useEffect(() => {
    const getHolidays = async () => {
      const year = currentDate.getFullYear();
      const month = currentDate.getMonth() + 1;

      const holidayList = await fetchHolidays(year, month);
      setHolidays(holidayList);
    };
    getHolidays();
  }, [currentDate]);

  // 커스텀 툴바 컴포넌트
  const CustomToolbar = ({ date, onNavigate }) => {
    return (
      <div className={styles.toolbar}>
        <button
          onClick={() => {
            onNavigate('PREV');
          }}
        >
          <img src="../../assets/arrowBackIcon.svg" alt="arrowPrev" />
        </button>
        <span>{moment(date).format('YYYY.MM')}</span>
        <button
          onClick={() => {
            onNavigate('NEXT');
          }}
        >
          <img src="../../assets/arrowBackIcon.svg" alt="arrowNext" className={styles.arrowNext} />
        </button>
      </div>
    );
  };
  // 커스텀 날짜 헤더 컴포넌트
  const CustomDateHeader = ({ label, date }) => {
    const formattedDate = moment(date).format('YYYY-MM-DD');
    const day = date.getDay();
    const holiday = holidays.find((item) => item.date === formattedDate);

    let className = '';

    if (holiday || day === 0) {
      className = styles.holidayHeader;
    } else if (day === 6) {
      className = styles.saturdayHeader;
    }

    return <span className={className}>{label}</span>;
  };

  // 날짜 스타일링 함수
  const customDayPropGetter = (date) => {
    const formattedDate = moment(date).format('YYYY-MM-DD');
    const attendance = attendanceData.find((item) => item.date === formattedDate);
    const holiday = holidays.find((item) => item.date === formattedDate);

    if (attendance) {
      if (attendance.status === 'ABSENCE') return { className: styles.absenceDay };
      if (attendance.status === 'EARlY_LEAVE') return { className: styles.earlyLeaveDay };
      if (attendance.status === 'LATE') return { className: styles.lateDay };
    }

    if (holiday) {
      return { className: styles.holidayDay };
    }

    return {};
  };

  // 공휴일을 이벤트로 표시
  const holidayEvents = holidays.map((holiday) => ({
    title: holiday.name,
    start: new Date(holiday.date),
    end: new Date(holiday.date),
    allDay: true,
  }));
  const formats = {
    monthHeaderFormat: 'YYYY MMMM',
    dayHeaderFormat: 'dddd',
    dayRangeHeaderFormat: ({ start, end }) =>
      `${moment(start).format('YYYY.MM.DD')} - ${moment(end).format('YYYY.MM.DD')}`,
  };
  return (
    <div className={styles.calendarContainer}>
      <BigCalendar
        localizer={localizer}
        events={holidayEvents}
        startAccessor="start"
        endAccessor="end"
        defaultView="month"
        date={currentDate}
        onNavigate={(date) => {
          setCurrentDate(date);
        }}
        dayPropGetter={customDayPropGetter}
        components={{
          toolbar: CustomToolbar,
          dateHeader: CustomDateHeader,
        }}
        view={view}
        onView={setView}
        formats={formats}
      />
    </div>
  );
}
