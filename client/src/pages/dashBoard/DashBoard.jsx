import React from 'react'
import { useSelector } from 'react-redux';
import styles from './DashBoard.module.css';
import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import Tag from '../../components/tag/Tag';

export default function DashBoard() {
  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar></SideBar>
      <div className={styles.dashBoardBox}>
        <Tab menuType={currentSideBarItem}></Tab>

        <div className={styles.dashBoardContent}>
          <DashBoardItem width="100%">
            <Tag title="수강중"></Tag>
            <Tag title="조퇴"></Tag>
            <Tag title="수강 중단"></Tag>
          </DashBoardItem>
        </div>
      </div>
    </div>
  );
}
