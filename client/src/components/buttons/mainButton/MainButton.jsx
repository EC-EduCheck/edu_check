import React from 'react'
import styles from "./MainButton.module.css"

export default function MainButton(props) {
  const title = props.title
  return (
    <div>
      <button className={styles.mainButton} title={title}>{title}</button>
    </div>
  )
}
