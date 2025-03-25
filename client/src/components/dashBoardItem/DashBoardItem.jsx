import React from 'react'
import styles from "./DashBoardItem.module.css"

export default function DashBoardItem({ width, title }) {
  return (
    <div style={{ width: `${width}` }} className={styles.DashBoardItem}>{title}</div>
  );
}
