import React from 'react';
import styles from './SidebarItem.module.css';
import { useDispatch, useSelector } from 'react-redux';
import SidebarItemIcon from '../sidebarItemIcon/SidebarItemIcon';
import { updateNav } from '../../../store/slices/sideBarItemSlice';
import { useNavigate } from 'react-router-dom';

export default function SidebarItem({ index, item }) {
  const navigate = useNavigate();

  const { nav } = useSelector((state) => state.sideBarItem);
  const isActive = nav === item;

  return (
    <button className={styles.sidebarItem} onClick={() => navigate(item.path)}>
      <SidebarItemIcon isActive={isActive} src={item.icon}></SidebarItemIcon>
      <p className={isActive ? `${styles.active}` : ''}>{item.name}</p>
    </button>
  );
}
