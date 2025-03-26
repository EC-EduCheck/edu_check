import React from 'react';
import styles from './LeftLineListItem.module.css';
import { getBackgroundColor } from '../../../utils/buttonContentList';
import MoreButton from '../../buttons/moreButton/MoreButton';

// TODO : 관리자의 유고 결석 관리만 isClickable = true
export default function LeftLineListItem({ isClickable, handleClick, status, children }) {
  const categoryMapper = {
    ABSENCE: '결석',
    LATE: '지각',
    EARLY_LEAVE: '조퇴',
  };

  const bgColor = getBackgroundColor(status);
console.log(bgColor);

  return (
    <div
      {...(isClickable ? { onClick: handleClick } : {})}
      className={`${styles.leftLineListItem} ${isClickable && `${styles.active}`} ${bgColor ? styles[bgColor] : ''}`}
    >
      
      <p>{children.studentName}</p>
      <p>{status}</p>
      <p>{categoryMapper[children.category] || ''}</p>
      <p>{children.attached ? '첨부' : '미첨부'}</p>
      <p>{new Date(children.createdAt).toLocaleDateString()}</p>
      <p>{children.status ? '승인' : false ? '반려' : '미승인'}</p>

      {!isClickable && <MoreButton></MoreButton>}
    </div>
  );
}
