import React from 'react'
import styles from "./SidebarItem.module.css"
import { useDispatch, useSelector } from 'react-redux';
import SidebarItemIcon from '../sidebarItemIcon/SidebarItemIcon';
import { updateNav } from '../../../store/slices/sideBarItemSlice';

export default function SidebarItem({ index, item }) {
  const dispatch = useDispatch();

  const { nav } = useSelector((state) => state.sideBarItem);
  const isActive = nav === item;

  const handleClick = () => {
    dispatch(updateNav(item));
  };

  return (
    <button className={styles.sidebarItem} onClick={handleClick}>
      <SidebarItemIcon isActive={isActive} index={index}></SidebarItemIcon>
      <p className={isActive ? `${styles.active}` : ''}>{item}</p>
    </button>
  );
}
