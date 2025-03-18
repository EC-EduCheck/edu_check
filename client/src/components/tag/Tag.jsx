import React from 'react'
import styles from "./Tag.module.css"
import { getTagColors, getIsClickable } from '../../utils/tagList';

export default function Tag({ title }) {
  const tagColors = getTagColors(title);
  const isClickable = getIsClickable(title);

  const handleClick = () => {
    // dropbox
  };

  return (
    <button
      style={{ color: `${tagColors[1]}`, backgroundColor: `${tagColors[0]}` }}
      disabled={!isClickable}
      onClick={handleClick}
      className={styles.tag}
    >
      {title}
    </button>
  );
}
