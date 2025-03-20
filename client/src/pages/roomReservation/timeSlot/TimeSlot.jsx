import React from 'react'
import styles from './TimeSlot.module.css'

export default function TimeSlot() {
  const handleClick = () => {
    //TODO: 클릭시 예약 이벤트 처리
    console.log('클릭');
  };

  return <div className={styles.container} onClick={handleClick}></div>;
}
