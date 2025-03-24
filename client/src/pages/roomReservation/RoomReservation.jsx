import React, { useEffect, useState } from 'react';
import styles from './RoomReservation.module.css';
import TimeGrid from './timeGrid/TimeGrid';
import { reservationApi } from '../../api/reservationApi';

export default function RoomReservation() {
  // dummy

  const [meetingRooms, setMeetingRooms] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await reservationApi.getReservations();
        setMeetingRooms(response.data.data.meetingRooms);
      } catch (error) {
        console.error(error);
      }
    };
    fetchReservations();
  }, []);

  const roomList = meetingRooms.map((item, index) => {
    // key는 상위에서 묶어줘야 함
    return (
      <div key={index}>
        <div>{`회의실 ${item}`}</div>
        <TimeGrid reservationList={item}></TimeGrid>
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
