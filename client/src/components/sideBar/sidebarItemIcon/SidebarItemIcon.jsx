import React from 'react'
import styles from "./SidebarItemIcon.module.css"
import { sideBarIconList } from '../../../utils/sideBarList';

export default function SidebarItemIcon({ isActive, index }) {
  return (
    <div className={styles.iconBox}>
      <img className={isActive ? `${styles.active}` : ''} src={sideBarIconList[index]} alt="icon" />
    </div>
  );
}
