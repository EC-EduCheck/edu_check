import React, { useState } from 'react';
import styles from './Tag.module.css';
import { getTagColors, getIsClickable, tagList } from '../../utils/tagList';
import DropBoxButton from '../buttons/dropBoxButton/DropBoxButton';

export default function Tag({ title }) {
  const [isOpen, setIsOpen] = useState(false);
  const tagColors = getTagColors(title);
  const isClickable = getIsClickable(title);

  const handleOpenDropBox = () => {
    setIsOpen(true);
  };

  const handleCloseDropBox = () => {
    setIsOpen(false);
  };

  const dropBoxButtonList = tagList.map((item) => {
    return <DropBoxButton title={item}></DropBoxButton>;
  });

  return (
    <div className={styles.tagBox}>
      <button
        style={{ color: `${tagColors[1]}`, backgroundColor: `${tagColors[0]}` }}
        disabled={!isClickable}
        onClick={handleOpenDropBox}
        onBlur={handleCloseDropBox}
        className={styles.tag}
      >
        {title}
      </button>

      <div
        style={
          isClickable ? (isOpen ? { display: 'block' } : { display: 'none' }) : { display: 'none' }
        }
        className={styles.dropBox}
      >
        {dropBoxButtonList}
      </div>
    </div>
  );
}
