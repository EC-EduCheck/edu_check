import React from 'react'
import styles from "./LeftLineListItem.module.css"
import { getBackgroundColor } from '../../../utils/absenceAttendancesList';
import MoreButton from '../../buttons/moreButton/MoreButton';

export default function LeftLineListItem({ isClickable, handleClick, status, children }) {
  const backgroundColor = getBackgroundColor(status);

  return isClickable ? (
    <button
      disabled={!isClickable}
      onClick={handleClick}
      style={{ '--after-bg': backgroundColor }}
      className={styles.leftLineListItem}
    >
      {children}
    </button>
  ) : (
    <div style={{ '--after-bg': backgroundColor }} className={styles.leftLineListItem}>
      {children}
      <div>wldnf</div>

      {!isClickable && <MoreButton></MoreButton>}
    </div>
  );
}
