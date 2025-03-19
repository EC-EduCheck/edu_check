import React from 'react'
import { useSelector } from 'react-redux';
import styles from './DashBoard.module.css';
import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import RoundButton from '../../components/buttons/roundButton/RoundButton';

export default function DashBoard() {
  const currentSideBarItem = useSelector((state) => state.sideBarItem.nav);

  return (
    <div className={`container ${styles.dashBoard}`}>
      <SideBar></SideBar>
      <div className={styles.dashBoardBox}>
        <Tab menuType={currentSideBarItem}></Tab>

        {/* dashBoardContent 내부에 대시보드 및 컴포넌트 사용 */}
        <div className={styles.dashBoardContent}>
          <DashBoardItem width="100%">
            <RoundButton title="결석"></RoundButton>
            <RoundButton title="조퇴"></RoundButton>
          </DashBoardItem>
        </div>
      </div>
    </div>
  );
}
