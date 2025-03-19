import React from 'react'
import styles from "./MainButton.module.css"
import { getBgColor, getWidth } from '../../../utils/buttonContentList';

export default function MainButton({ title, handleClick }) {
  const autoStyle = {
    width: getWidth(title),
    backgroundColor: getBgColor(title),
  };

  return (
    <button className={styles.mainButton} onClick={handleClick} style={autoStyle}>
      {title}
    </button>
  );
}
