import React from 'react';
import styles from './SidebarItem.module.css';
import SidebarItemIcon from '../sidebarItemIcon/SidebarItemIcon';
import { useNavigate } from 'react-router-dom';

export default function SidebarItem({ index, item, isActive }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(item.path);
  };

  return (
    <button className={styles.sidebarItem} onClick={handleClick}>
      <SidebarItemIcon icon={item.icon} isActive={isActive}></SidebarItemIcon>
      <p className={isActive ? `${styles.active}` : ''}>{item.name}</p>
    </button>
  );
}
