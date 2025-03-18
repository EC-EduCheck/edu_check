import React from 'react'
import styles from "./InfoItem.module.css"

export default function InfoItem({ title, content }) {
  return (
    <div className={styles.infoItem}>
      <p>{title}</p>
      <p>{content}</p>
    </div>
  );
}
