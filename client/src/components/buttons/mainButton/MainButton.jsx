import React from 'react'
import styles from "./MainButton.module.css"

export default function MainButton({title, handleClick, bgColor}) {

  return (
    <div>
      <button className={styles.mainButton} onClick={handleClick} style={{backgroundColor : bgColor}}>{title}</button>
    </div>
  )
}
