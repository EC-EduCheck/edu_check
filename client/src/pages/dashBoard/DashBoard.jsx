import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './DashBoard.module.css';

import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import { useSelector } from 'react-redux';
import { navbarPath } from '../../constants/navbarPath';

export default function DashBoard() {
  const { role } = useSelector((state) => state.auth.user);

  const renderNavbarPath = navbarPath?.[role];
  console.log(renderNavbarPath, '---------');

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar />
      <Outlet></Outlet>
    </div>
  );
}
