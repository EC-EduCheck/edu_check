import React from 'react'
import styles from "./DashBoardItem.module.css"

export default function DashBoardItem({ width, children }) {
  return (
    <div style={{ width: `${width}` }} className={styles.DashBoardItem}>
      {children}
    </div>
  );
}
