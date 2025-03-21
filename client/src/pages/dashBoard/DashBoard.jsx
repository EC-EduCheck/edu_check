import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './DashBoard.module.css';

import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
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

        {/* TODO : dashBoardContent 내부에 대시보드 및 컴포넌트 사용 */}
        <div className={styles.dashBoardContent}>
          <Outlet></Outlet>
          <DashBoardItem width="100%"></DashBoardItem>
        </div>
      </div>
    </div>
  );
}
