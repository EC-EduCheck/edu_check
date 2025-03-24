import React from 'react'
import HourBlock from '../hourBlock/HourBlock'
import styles from './TimeGrid.module.css'

export default function TimeGrid({ reservationList }) {
  const { reservations } = reservationList;
  console.log(reservations);

  //9시 ~ 22시
  const MIN_HOUR = 9;
  const MAX_HOUR = 22;

  const extractTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hours = date.getHours();
    const minutes = date.getMinutes();
    return { hours, minutes };
  };

    // 예약 목록을 필터링하고 정렬
    const filteredReservations = reservations
    .map((reservation) => {
      const { hours, minutes } = extractTime(reservation.startDateTime);
      return { ...reservation, hours, minutes };
    })
    .filter(({ hours }) => hours >= MIN_HOUR && hours < MAX_HOUR) // 9시~22시까지만 허용
    .sort((a, b) => a.hours * 60 + a.minutes - (b.hours * 60 + b.minutes)); // 시간순 정렬

  const hourList = filteredReservations.map((reservation) => {
    const { startDateTime, hours, minutes } = reservation;
    console.log(`예약 시간: ${hours}:${minutes}`);

    return <HourBlock hour={hours} data={reservation} key={startDateTime}></HourBlock>;
  });

  return <div className={styles.container}>{hourList}</div>;
}


//   // const TIME = Array.from({ length: 14 }, (_, i) => i + 9);
//   let TIME = 900;

//   const hourList = reservations.map((reservation, index) => {
//     console.log('reservation', reservation);
//     const { startDateTime } = item;
//     console.log(startDateTime);
//     return <HourBlock hour={index} data={reservation} key={startDateTime}></HourBlock>;
//   });

//   return <div className={styles.container}>{hourList}</div>;
// }
