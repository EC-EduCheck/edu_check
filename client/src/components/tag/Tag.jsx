import React, { useEffect, useRef, useState } from 'react';
import styles from './Tag.module.css';
import { getTagColors, getIsClickable, tagList } from '../../utils/buttonContentList';
import DropBoxButton from '../buttons/dropBoxButton/DropBoxButton';
import { studentManageApi } from '../../api/studentManageApi';

export default function Tag({ title, studentId, courseId }) {
  const [currentTitle, setCurrentTitle] = useState(title);
  const tagColors = getTagColors(currentTitle);
  const isClickable = getIsClickable(title);

  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef(null);
  const statusMapper = {
    등록전: 'PREVIOUS',
    수강중: 'PROGRESS',
    수료: 'COMPLETED',
    '수강 중단': 'DROPPED',
  };

  const handleToggleDropBox = () => {
    setIsOpen((prev) => !prev);
  };

  const handleTagClick = (newTagTitle) => {
    const status = statusMapper[newTagTitle];
    try{
      studentManageApi.modifyStudentStatus(courseId, studentId, status);
      setCurrentTitle(newTagTitle);
    } catch (error) {
      console.error(error);
    }


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
        {currentTitle}
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
