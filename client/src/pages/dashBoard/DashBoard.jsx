import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './DashBoard.module.css';

import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

export default function DashBoard() {
  const { path } = useSelector((state) => state.sideBarItem);
  const navigate = useNavigate();

  useEffect(() => {
    navigate(path);
  }, [path]);

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar />
      <div className={styles.dashBoardBox}>
        <Tab />

        {/* TODO : 각 페이지 내부에 DashBoardItem 사용 */}
        <div className={styles.dashBoardContent}>
          <Outlet></Outlet>
        </div>
      </div>
    </div>
  );
}
