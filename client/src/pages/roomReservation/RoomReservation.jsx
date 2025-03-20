import React from 'react'
import styles from './RoomReservation.module.css'
import TimeGrid from './timeGrid/TimeGrid'


export default function RoomReservation() {
  return (
    <div className={styles.container}>
      <TimeGrid></TimeGrid>
    </div>
  )
}
