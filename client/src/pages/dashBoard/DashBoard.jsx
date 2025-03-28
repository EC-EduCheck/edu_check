import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './DashBoard.module.css';

import SideBar from '../../components/sideBar/SideBar';
export default function DashBoard() {
  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar />
      <Outlet></Outlet>
    </div>
  );
}
