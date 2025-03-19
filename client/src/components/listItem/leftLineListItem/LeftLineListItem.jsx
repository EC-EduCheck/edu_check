import React from 'react'
import styles from "./LeftLineListItem.module.css"
import { getBackgroundColor } from '../../../utils/buttonContentList';
import MoreButton from '../../buttons/moreButton/MoreButton';

// TODO : 관리자의 유고 결석 관리만 isClickable = true
export default function LeftLineListItem({ isClickable, handleClick, status, children }) {
  const bgColor = getBackgroundColor(status);

  return (
    <div
      // TODO : 관리자의 유고 결석 관리만 handleClick 사용
      // onClick={handleClick}
      className={`${styles.leftLineListItem} ${isClickable && `${styles.active}`} ${bgColor ? styles[bgColor] : ''}`}
    >
      {children}

      {!isClickable && <MoreButton></MoreButton>}
    </div>
  );
}
