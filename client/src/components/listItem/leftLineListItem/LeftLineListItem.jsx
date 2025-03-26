import React from 'react';
import styles from './LeftLineListItem.module.css';
import { getBackgroundColor } from '../../../utils/buttonContentList';
import MoreButton from '../../buttons/moreButton/MoreButton';

// TODO : 관리자의 유고 결석 관리만 isClickable = true
export default function LeftLineListItem({ isClickable, handleClick, status, children }) {
  const bgColor = getBackgroundColor(status);

  return (
    <div
      {...(isClickable ? { onClick: handleClick } : {})}
      className={`${styles.leftLineListItem} ${isClickable && `${styles.active}`} ${bgColor ? styles[bgColor] : ''}`}
    >
      {children.studentName}
      {status}
      {/* 출석상태 */}
      {children.attached ? '첨부' : '미첨부'}
      {children.status ? '승인' : false ? '반려' : '미승인'}

      {!isClickable && <MoreButton></MoreButton>}
    </div>
  );
}
