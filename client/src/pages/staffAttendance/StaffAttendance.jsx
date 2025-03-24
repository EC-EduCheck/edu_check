import React, { useState } from 'react';
import styles from './StaffAttendance.module.css';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import FilterButton from '../../components/buttons/filterButton/FilterButton';

export default function StaffAttendance() {
  const list = ['출석', '조퇴', '지각', '결석'];

  const [isActiveIndex, setIsActiveIndex] = useState(false);
  const handleActiveFilter = (index) => {
    if (index === isActiveIndex) {
      setIsActiveIndex(false);
    } else {
      setIsActiveIndex(index);
    }
  };

  const filterButtons = list.map((item, index) => {
    return (
      <FilterButton
        key={index}
        index={index}
        isActiveIndex={isActiveIndex}
        title={item}
        content="18명"
        handleActiveFilter={handleActiveFilter}
      ></FilterButton>
    );
  });

  return (
    <div>
      <DashBoardItem width="100%">
        <>
          <h2 className="subTitle">출결 현황</h2>
          <div style={{ display: 'flex', width: '100%', gap: '1rem' }}>{filterButtons}</div>
        </>
      </DashBoardItem>
    </div>
  );
}
