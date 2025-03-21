import React from 'react'
import styles from './RoomReservation.module.css'
import TimeGrid from './timeGrid/TimeGrid'


export default function RoomReservation() {
  // dummy
  const roomCount = [1, 2, 3, 4];

  const roomList = roomCount.map((item, index) => {
    return (
      <div>
        <div>{`회의실 ${item}`}</div>
        <TimeGrid key={index}></TimeGrid>
      </div>
    );
  });

  return (
    <>
      <div div className={styles.container}>
        {roomList}
      </div>
    </>
  );
}
