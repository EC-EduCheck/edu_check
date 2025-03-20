import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Outlet, useNavigate } from 'react-router-dom';

import styles from './DashBoard.module.css';
import { sideBarList } from '../../utils/sideBarList';
import { studentNavList, getStudentTabList } from '../../utils/dashBoardList';

import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';

export default function DashBoard() {
  const navigate = useNavigate();
  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);
  const { nav, tab } = useSelector((state) => state.sideBarItem);

  // TODO: 로그인 시 router에서 role 받아오기 (STUDENT, ADMIN)
  const role = 'staff';

  // sideBar와 tab에 따라 페이지 변경
  useEffect(() => {
    const navIndex = sideBarList.indexOf(nav);

    if (tab > 0) {
      const tabList = getStudentTabList(nav, tab);
      navigate(`${studentNavList[navIndex]}/${tabList}`);
    } else {
      navigate(`${studentNavList[navIndex]}`);
    }
  }, [nav, tab]);

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
