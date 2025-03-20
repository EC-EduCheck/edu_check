import React, { useEffect, useState, useRef } from 'react';
import { useSelector } from 'react-redux';

import styles from './SideBar.module.css';
import { studentSideBarList, staffSideBarList } from '../../utils/sideBarList';
import { roleList } from '../../utils/dashBoardList';

import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';

export default function SideBar() {
  const infoRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const [itemList, setItemList] = useState([]);
  const { name, role, courseName, phoneNumber, birthDate, email } = useSelector(
    (state) => state.auth.user,
  );

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (infoRef.current && !infoRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  useEffect(() => {
    if (role === roleList[0]) {
      setItemList(studentSideBarList);
    } else {
      setItemList(staffSideBarList);
    }
  }, [role]);

  const sideBarItems = itemList.map((item, index) => {
    return <SideBarItem key={index} index={index} item={item}></SideBarItem>;
  });

  return (
    <div className={styles.sideBar}>
      <div ref={infoRef} onClick={() => setIsOpen(true)} className={styles.memberInfo}>
        <div className={styles.memberInfoImg}>
          <img src="../../assets/logo.png" alt="user image" />
        </div>

        <div className={styles.memberInfoDetail}>
          <h1>{name}</h1>
          <p>{courseName}</p>
        </div>

        <div className={`${styles.memberInfoMore} ${isOpen && `${styles.isOpen}`}`}>
          <ul>
            <li>
              <p>생년월일</p>
              <p>{birthDate}</p>
            </li>
            <li>
              <p>연락처</p>
              <p>{phoneNumber}</p>
            </li>
            <li>
              <p>이메일</p>
              <p>{email}</p>
            </li>
          </ul>
          {/* todo: 로그아웃 기능 추가 */}
          <MainButton title="로그아웃"></MainButton>
        </div>
      </div>

      {/* todo: 출석하기 기능 추가 */}
      <MainButton title="출석하기"></MainButton>
      <nav>{sideBarItems}</nav>
    </div>
  );
}
