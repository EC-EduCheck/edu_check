import React from 'react'
import styles from "./SidebarItemIcon.module.css"
import { sideBarIconList } from '../../../utils/sideBarList';

export default function SidebarItemIcon({ isCurrent, index }) {
  const currentStyle = isCurrent ? { filter: 'opacity(0.5) drop-shadow(0 0 0 #666666)' } : {};

  return (
    <div className={styles.iconBox}>
      <img style={currentStyle} src={sideBarIconList[index]} alt="icon" />
    </div>
  );
}
