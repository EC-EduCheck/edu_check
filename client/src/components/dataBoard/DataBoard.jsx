import React from 'react'
import styles from "./DataBoard.module.css"

export default function DataBoard({ title, data }) {
  return (
    <div className={styles.dataBoard}>
      <p>{title}</p>
      <p>{data}</p>
    </div>
  );
}
