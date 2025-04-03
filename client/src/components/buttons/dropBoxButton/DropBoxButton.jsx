import React from 'react';
import styles from './DropBoxButton.module.css';

export default function DropBoxButton({ title, handleClick }) {
  return (
    <button className={styles.dropBoxButton} onClick={handleClick}>
      {title}
    </button>
  );
}
