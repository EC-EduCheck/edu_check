import React from 'react'
import styles from "./DashBoard.module.css"
import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import { useSelector } from 'react-redux';

export default function DashBoard() {
  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar></SideBar>
      <div className={styles.dashBoardBox}>
        <Tab menuType={currentSideBarItem}></Tab>
        <div className={styles.dashBoardContent}></div>
      </div>
    </div>
  );
}
