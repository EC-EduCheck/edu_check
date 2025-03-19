import React, { useState } from 'react';
import styles from './MainButton.module.css';
import { activeTitle, fullWidthTitle } from '../../../utils/buttonContentList';

export default function MainButton({ title, handleClick }) {
  const isActive = activeTitle.includes(title);
  const isFullWidth = fullWidthTitle.includes(title);

  return (
    <button
      className={`${styles.mainButton} ${isFullWidth && `${styles.fullWidth}`} ${isActive && `${styles.active}`}`}
      onClick={handleClick}
    >
      {title}
    </button>
  );
}
