import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styles from './SidebarItemIcon.module.css';
import { studentSideBarIconList, staffSideBarIconList } from '../../../utils/sideBarList';
import { roleList } from '../../../utils/dashBoardList';

export default function SidebarItemIcon({ isActive, index }) {
  const { role } = useSelector((state) => state.auth.user);
  const [icons, setIcons] = useState([]);

  useEffect(() => {
    if (role === roleList[0]) {
      setIcons(studentSideBarIconList);
    } else {
      setIcons(staffSideBarIconList);
    }
  }, [role]);

  return (
    <div className={styles.iconBox}>
      <img className={isActive ? `${styles.active}` : ''} src={icons[index]} alt="icon" />
    </div>
  );
}
