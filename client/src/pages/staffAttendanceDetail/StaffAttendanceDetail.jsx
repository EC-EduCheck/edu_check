import React, { useState } from 'react';
import styles from './StaffAttendanceDetail.module.css';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import DataBoard from '../../components/dataBoard/DataBoard';

export default function StaffAttendanceDetail() {
  const dummyUser = {
    name: '홍길동',
    phone: '010-1234-5678',
  };

  const attendanceItems = [
    { id: 1, content: '2024년 01월 01일', tagTitle: '결석' },
    { id: 2, content: '2024년 01월 01일', tagTitle: '조퇴' },
    { id: 3, content: '2024년 01월 01일', tagTitle: '출석' },
    { id: 4, content: '2024년 01월 01일', tagTitle: '출석' },
    { id: 5, content: '2024년 01월 01일', tagTitle: '지각' },
    { id: 6, content: '2024년 01월 01일', tagTitle: '결석' },
    { id: 7, content: '2024년 01월 01일', tagTitle: '결석' },
    { id: 8, content: '2024년 01월 01일', tagTitle: '결석' },
    { id: 19, content: '2024년 01월 01일', tagTitle: '조퇴' },
    { id: 29, content: '2024년 01월 01일', tagTitle: '조퇴' },
    { id: 39, content: '2024년 01월 01일', tagTitle: '조퇴' },
    { id: 49, content: '2024년 01월 01일', tagTitle: '조퇴' },
    { id: 59, content: '2024년 01월 01일', tagTitle: '출석' },
  ];

  return (
    <div className={styles.container}>
      <div className={styles.dashboardContainer}>
        <DashBoardItem>
          <div className={styles.userInfoContainer}>
            <div className={styles.userInfo}>
              <h2 className={styles.userName}>{dummyUser.name}</h2>
              <p className={styles.userPhone}>{dummyUser.phone}</p>
            </div>
          </div>

          <div className={styles.databoardContainer}>
            <DataBoard title="금일 기준 출석률" data="0%" />
            <DataBoard title="전체 출석률" data="0%" />
            <DataBoard title="과정 진행률" data="0%" />
          </div>
        </DashBoardItem>
      </div>

      <div className={styles.contentWrapper}>
        <div className={styles.listContainer}>
          {attendanceItems.map((item) => (
            <BaseListItem
              key={`attendance-${item.id}`}
              id={item.id}
              content={item.content}
              tagTitle={item.tagTitle}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
