import React, { useState } from 'react';
import styles from './SideBar.module.css';
import { sideBarList } from '../../utils/sideBarList';
import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';

export default function SideBar() {
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenInfoMore = () => {
    setIsOpen(true);
  };

  const handleCloseInfoMore = () => {
    setIsOpen(false);
  };

  const sideBarItems = sideBarList.map((item, index) => {
    return <SideBarItem key={index} index={index} item={item}></SideBarItem>;
  });

  return (
    <div className={styles.sideBar}>
      <button
        onClick={handleOpenInfoMore}
        onBlur={handleCloseInfoMore}
        className={styles.memberInfo}
      >
        <div className={styles.memberInfoImg}>
          <img src="./assets/logo.png" alt="user image" />
        </div>

        {/* todo : member 이름, 과정 가져오기 */}
        <div className={styles.memberInfoDetail}>
          <h1>수강생</h1>
          <p>클라우드 기반 JAVA 풀스택 웹개발</p>
        </div>

        {/* todo : member 개인정보 변수 필요 */}
        <div
          style={isOpen ? { display: 'block' } : { display: 'none' }}
          className={styles.memberInfoMore}
        >
          <ul>
            <li>
              <p>생년월일</p>
              <p>2025.01.01</p>
            </li>
            <li>
              <p>연락처</p>
              <p>010-1234-5678</p>
            </li>
            <li>
              <p>이메일</p>
              <p>hoho@example.com</p>
            </li>
          </ul>
          <MainButton></MainButton>
        </div>
      </button>

      {/* todo: 메인 버튼 스타일 추가 */}
      <MainButton />
      <nav>{sideBarItems}</nav>
    </div>
  );
}
