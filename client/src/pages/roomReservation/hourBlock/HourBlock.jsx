import React from 'react'
import TimeSlot from '../timeSlot/TimeSlot'
import styles from './HourBlock.module.css'

export default function HourBlock({ hour }) {
  const MINUTES = [0, 15, 30, 45];

  return (
    <div className={styles.container}>
      {MINUTES.map((item, index) => {
        const showTime = index === 0;

        return (
          <div className={styles.timeSlotWrapper} key={index}>
            {showTime && <div className={styles.timeLabel}>{`${hour}:00`}</div>}
            <TimeSlot />
          </div>
        );
      })}
    </div>
  );
}
