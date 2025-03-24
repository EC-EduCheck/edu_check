import React from 'react';
import TimeSlot from '../timeSlot/TimeSlot';
import styles from './HourBlock.module.css';

export default function HourBlock({ hour, data }) {
  const MINUTES = [0, 15, 30, 45]; // 15분 단위로 시간 슬롯 나누기
  console.log('data', data);

  // 해당 시간대에 예약된 데이터가 있는지 확인하는 함수
  const getReservation = (minute) => {
    // 예약 시간과 분을 비교하여 해당 시간에 예약된 데이터가 있는지 찾기
    const reservation = data.find(
      (reservation) => reservation.hours === hour && reservation.minutes === minute,
    );
    return reservation;
  };

  return (
    <div className={styles.container}>
      {MINUTES.map((minute, index) => {
        const reservation = getReservation(minute); // 해당 분에 예약된 데이터 가져오기
        const showTime = index === 0; // 첫 번째 슬롯에만 시간 레이블 표시

        return (
          <div className={styles.timeSlotWrapper} key={index}>
            {showTime && (
              <div className={styles.timeLabel}>{`${hour}:${minute < 10 ? '0' : ''}${minute}`}</div>
            )}
            {/* 예약된 데이터가 있으면 예약 정보를 표시하고, 없으면 빈 슬롯 */}
            <TimeSlot reservation={reservation} />
          </div>
        );
      })}
    </div>
  );
}
