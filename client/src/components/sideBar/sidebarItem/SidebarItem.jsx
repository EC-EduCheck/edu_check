import React from 'react'
import styles from "./SidebarItem.module.css"
import { useDispatch, useSelector } from 'react-redux';
import SidebarItemIcon from '../sidebarItemIcon/SidebarItemIcon';
import { updateNav } from '../../../store/slices/sideBarItemSlice';

export default function SidebarItem({ index, item }) {
  const dispatch = useDispatch();

  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);
  const currentStyle = currentSideBarItem === item ? { color: '#666' } : {};
  const isCurrent = currentSideBarItem === item;

  const handleClick = () => {
    dispatch(updateNav(item));
  };

  return (
    <button className={styles.sidebarItem} onClick={handleClick}>
      <SidebarItemIcon isCurrent={isCurrent} index={index}></SidebarItemIcon>
      <p style={currentStyle}>{item}</p>
    </button>
  );
}
