import React from 'react'
import styles from './TimeSlot.module.css'

export default function TimeSlot({ handleClick}) {
  return (
    <div className={styles.container} onClick={handleClick}></div>
  )
}
