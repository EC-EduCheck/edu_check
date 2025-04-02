import React, { useEffect, useRef, useState } from 'react';
import styles from './Tag.module.css';
import { getTagColors, getIsClickable, tagList } from '../../utils/buttonContentList';
import DropBoxButton from '../buttons/dropBoxButton/DropBoxButton';

export default function Tag({ title }) {
  const tagColors = getTagColors(title);
  const isClickable = getIsClickable(title);

  // TODO : 관리자 페이지의 학습자 관리 tag에만 사용
  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef(null);

  const handleToggleDropBox = () => {
    setIsOpen((prev) => !prev);
  };

  const handleCloseDropBox = () => {
    setIsOpen(false);
  };

  const handleTagClick = (newTagTitle) => {
    // TODO : patch 요청
    setIsOpen(false);
  };

  const dropBoxButtonList = tagList.map((item, index) => (
    <DropBoxButton key={index} title={item} handleClick={() => handleTagClick(item)} />
  ));

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (containerRef.current && !containerRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div ref={containerRef} className={styles.tagBox}>
      <button
        disabled={!isClickable}
        onClick={handleToggleDropBox}
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
