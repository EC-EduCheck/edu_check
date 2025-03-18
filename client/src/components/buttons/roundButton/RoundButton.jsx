import React from 'react'
import styles from "./RoundButton.module.css"

export default function RoundButton({title, handleClick, bgcolor}) {
  return (
    <div>
      <button className={styles.roundButton} onClick={handleClick} style={{backgroundColor : bgcolor}}>{title}</button>
    </div>
  )
}
