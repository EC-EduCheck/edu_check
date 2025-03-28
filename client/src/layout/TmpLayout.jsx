import React from 'react';
import Tab from '../components/tab/Tab';
import { Outlet } from 'react-router-dom';
import styles from '../pages/dashBoard/DashBoard.module.css';

export default function TmpLayout({ tabList }) {
  return (
    <>
      <div className={styles.dashBoardBox}>
        <Tab />
        {/* TODO : dashBoardContent 내부에 대시보드 및 컴포넌트 사용 */}
        <div className={styles.dashBoardContent}>
          <Outlet></Outlet>
        </div>
      </div>
    </>
  );
}
