import React from 'react';
import styles from './CircleButton.module.css';

function CircleButton({ onClick: handleClick = null, title, isEnable }) {
  if (!isEnable) return;
  return (
    <button className={styles.circleButton} onClick={handleClick}>
      <span className={styles.buttonTitle}>{title}</span>
    </button>
  );
}

export default CircleButton;
