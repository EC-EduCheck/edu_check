import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Outlet, useNavigate } from 'react-router-dom';

import styles from './DashBoard.module.css';
import { roleList } from '../../utils/dashBoardList';
import { studentSideBarList, staffSideBarList } from '../../utils/sideBarList';
import {
  studentNavList,
  getStudentTabList,
  staffNavList,
  getStaffTabList,
} from '../../utils/dashBoardList';

import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';

export default function DashBoard() {
  const navigate = useNavigate();
  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);
  const { nav, tab } = useSelector((state) => state.sideBarItem);
  const { role } = useSelector((state) => state.auth.user);

  useEffect(() => {
    if (role === roleList[0]) {
      const navIndex = studentSideBarList.indexOf(nav);

      if (tab > 0) {
        const tabList = getStudentTabList(nav, tab);
        navigate(`${studentNavList[navIndex]}/${tabList}`);
      } else {
        navigate(`${studentNavList[navIndex]}`);
      }
    } else {
      const navIndex = staffSideBarList.indexOf(nav);

      if (tab > 0) {
        const tabList = getStaffTabList(nav, tab);
        navigate(`${staffNavList[navIndex]}/${tabList}`);
      } else {
        navigate(`${staffNavList[navIndex]}`);
      }
    }
  }, [role, nav, tab]);

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar />
      <div className={styles.dashBoardBox}>
        <Tab menuType={currentSideBarItem}></Tab>

        {/* TODO : dashBoardContent 내부에 대시보드 및 컴포넌트 사용 */}
        <div className={styles.dashBoardContent}>
          <Outlet></Outlet>
          <DashBoardItem width="100%"></DashBoardItem>
        </div>
      </div>
    </div>
  );
}
