import React from 'react';
import { useSelector } from 'react-redux';
import styles from './SidebarItemIcon.module.css';

export default function SidebarItemIcon({ isActive, icon }) {
  return (
    <div className={styles.iconBox}>
      <img className={isActive ? `${styles.active}` : ''} src={icon} alt="icon" />
    </div>
  );
}
