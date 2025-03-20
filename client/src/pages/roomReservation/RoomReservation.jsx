import React from 'react'
import styles from './RoomReservation.module.css'
import TimeGrid from './timeGrid/TimeGrid'


export default function RoomReservation() {
  // dummy
  const roomCount = [1, 2, 3];

  const roomList = roomCount.map((item, index) => {
    return (
      <>
        <TimeGrid key={index}></TimeGrid>
      </>
    );
  });

  return (
    <>
      <div div className={styles.container}>
        <h3>회의실 예약</h3>
        {roomList}
      </div>
    </>
  );
}
