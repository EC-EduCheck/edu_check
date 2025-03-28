import React from 'react';
import styles from './SidebarItem.module.css';
import { useSelector } from 'react-redux';
import SidebarItemIcon from '../sidebarItemIcon/SidebarItemIcon';
import { useNavigate } from 'react-router-dom';

export default function SidebarItem({ index, item }) {
  const navigate = useNavigate();

  // const { nav } = useSelector((state) => state.sideBarItem);
  // const isActive = nav === item; // 예전코드 지금의 item.name
  // // nav -> url에서 특정 segment를 가져오기

  return (
    <button className={styles.sidebarItem} onClick={() => navigate(item.path)}>
      {/* <SidebarItemIcon isActive={isActive} src={item.icon}></SidebarItemIcon> */}
      {/* <p className={isActive ? `${styles.active}` : ''}>{item.name}</p> */}
      <SidebarItemIcon src={item.icon}></SidebarItemIcon>
      <p>{item.name}</p>
    </button>
  );
}
