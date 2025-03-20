import React from 'react'
import TimeSlot from '../timeSlot/TimeSlot'
import styles from './HourBlock.module.css'

export default function HourBlock() {

  const MINITUES = [0, 15, 30, 45]; 

  const list = MINITUES.map((item, index) => {
    return <TimeSlot key={index}></TimeSlot>;
  });

  return (
    <div className={styles.container}>
      {list}
    </div>
  )
}
