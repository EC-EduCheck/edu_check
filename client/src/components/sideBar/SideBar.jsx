import React from 'react'
import styles from "./SideBar.module.css"
import { sideBarList } from '../../utils/sideBarList';
import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';

export default function SideBar() {
  const sideBarItems = sideBarList.map((item, index) => {
    return <SideBarItem key={index} index={index} item={item}></SideBarItem>;
  });

  return (
    <div className={styles.sideBar}>
      <div className={styles.memberInfo}>
        <div className={styles.memberInfoImg}>
          <img src="./assets/calender-icon.png" alt="user image" />
        </div>

        {/* todo : member 이름, 과정 가져오기 */}
        <div className={styles.memberInfoDetail}>
          <h1>수강생</h1>
          <p>클라우드 기반 JAVA 풀스택 웹개발</p>
        </div>
      </div>

      {/* todo: 메인 버튼 스타일 추가 */}
      <MainButton />
      <nav>{sideBarItems}</nav>
    </div>
  );
}
