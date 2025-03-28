import React, { useState } from 'react';
import styles from './Tag.module.css';
import { getTagColors, getIsClickable, tagList } from '../../utils/buttonContentList';
import DropBoxButton from '../buttons/dropBoxButton/DropBoxButton';

export default function Tag({ title, onTagChange }) {
  const tagColors = getTagColors(title);
  const isClickable = getIsClickable(title);

  // TODO : 관리자 페이지의 학습자 관리 tag에만 사용
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenDropBox = () => {
    setIsOpen(true);
  };

  const handleCloseDropBox = () => {
    setIsOpen(false);
  };

  const handleTagClick = (newTagTitle) => {
    onTagChange(newTagTitle);
    setIsOpen(false);
  };

  const dropBoxButtonList = tagList.map((item, index) => (
    <DropBoxButton key={index} title={item} handleClick={() => handleTagClick(item)} />
  ));

  return (
    <div className={styles.tagBox}>
      <button
        disabled={!isClickable}
        onClick={handleOpenDropBox}
        // onBlur={handleCloseDropBox}
        className={`${styles.tag} ${tagColors ? styles[tagColors] : ''}`}
      >
        {title}
      </button>

      {/* TODO : 관리자 페이지의 학습자 관리 tag에만 사용 */}
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
