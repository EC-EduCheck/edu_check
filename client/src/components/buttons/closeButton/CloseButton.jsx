import React from 'react'
import styles from './CloseButton.module.css';

export default function CloseButton({ handleClick }) {
  return (
    <button onClick={handleClick} className={styles.closeButton}>
      <img src="../../assets/close-icon.png" alt="닫기 아이콘" />
    </button>
  );
}
