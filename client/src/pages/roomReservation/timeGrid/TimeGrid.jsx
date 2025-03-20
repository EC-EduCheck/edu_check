import React from 'react'
import HourBlock from '../hourBlock/HourBlock'
import styles from './TimeGrid.module.css'

export default function TimeGrid() {

  //9시 ~ 22시
  const TIME = Array.from({ length: 14 }, (_, i) => i + 9);

  const hourList = TIME.map((item) => {
    return <HourBlock key={item}></HourBlock>
  }) 

  return <div className={styles.container}>{hourList}</div>;
}
