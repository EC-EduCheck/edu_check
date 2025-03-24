import React from 'react'
import styles from './ProgressBar.module.css'

export default function ProgressBar({ value, max, startDate, endDate }) {
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return `${date.getFullYear()}.${date.getMonth() + 1}.${date.getDate()}`;
  };
  
  const progressPercentage = (value / max) * 100;
  
  return (
    <div className={styles.progressContainer}>
      <div className={styles.markerContainer}>
        <div 
          className={styles.progressMarker}
          style={{ left: `${progressPercentage}%` }}
        >
          {value}
        </div>
      </div>
      <div className={styles.progressBar}>
        <div 
          className={styles.progressBarCompleted} 
          style={{ width: `${progressPercentage}%` }}
        >
        </div>
      </div>
      <div className={styles.dateLabels}>
        <span className={styles.startDate}>{formatDate(startDate)}</span>
        <span className={styles.endDate}>{formatDate(endDate)}</span>
      </div>
    </div>
  );
}
