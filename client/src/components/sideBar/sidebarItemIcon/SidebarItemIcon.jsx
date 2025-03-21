import React from 'react';
import { useSelector } from 'react-redux';
import styles from './SidebarItemIcon.module.css';

export default function SidebarItemIcon({ isActive, index }) {
  const { sidebarIconList } = useSelector((state) => state.sideBarItem);

  return (
    <div className={styles.iconBox}>
      <img className={isActive ? `${styles.active}` : ''} src={sidebarIconList[index]} alt="icon" />
    </div>
  );
}
