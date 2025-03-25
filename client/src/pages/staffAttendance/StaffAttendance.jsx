import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import styles from './StaffAttendance.module.css';
import { attendanceApi } from '../../api/attendanceApi';

import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import FilterButton from '../../components/buttons/filterButton/FilterButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';

export default function StaffAttendance() {
  const [isActiveIndex, setIsActiveIndex] = useState(false);
  const [dataList, setDataList] = useState([]);
  const { courseId } = useSelector((state) => state.auth.user);
  // TODO : UI 구현을 위한 예시 students
  const [students, setStudents] = useState([
    {
      studentId: 1,
      studentName: '홍길동',
      status: 'ATTENDANCE',
    },
    {
      studentId: 2,
      studentName: '김수진',
      status: 'LATE',
    },
    {
      studentId: 3,
      studentName: '이영훈',
      status: 'EARLY_LEAVE',
    },
    {
      studentId: 4,
      studentName: '박연정',
      status: 'ATTENDANCE',
    },
  ]);

  const getAttendances = async () => {
    try {
      const response = await attendanceApi.getTodayAttendances(courseId);
      const { attence, early, late, absence } = response.data.data;
      setDataList([
        { label: '출석', value: attence },
        { label: '조퇴', value: early },
        { label: '지각', value: late },
        { label: '결석', value: absence },
      ]);
      // setStudents(response.data.students);
    } catch (error) {
      // TODO : 에러 처리 필요
      console.log(error);
    }
  };

  useEffect(() => {
    getAttendances();
  }, [isActiveIndex]);

  const handleActiveFilter = (index) => {
    if (index === isActiveIndex) {
      setIsActiveIndex(false);
    } else {
      setIsActiveIndex(index);
    }
  };

  const filterButtons = dataList.map((item, index) => {
    return (
      <FilterButton
        key={index}
        index={index}
        isActiveIndex={isActiveIndex}
        title={item.label}
        content={item.value}
        handleActiveFilter={handleActiveFilter}
      ></FilterButton>
    );
  });

  const studentsList = students.map((item, index) => {
    const tag = {
      ATTENDANCE: '출석',
      LATE: '지각',
      EARLY_LEAVE: '조퇴',
    };

    if (
      (typeof isActiveIndex === 'number' && dataList[isActiveIndex].label === tag[item.status]) ||
      isActiveIndex === false
    ) {
      return (
        <BaseListItem
          key={index}
          content={item.studentName}
          tagTitle={tag[item.status]}
        ></BaseListItem>
      );
    }
  });

  return (
    <div>
      <DashBoardItem width="100%">
        <>
          <h2 className="subTitle">출결 현황</h2>
          <div className={styles.filterButtonBox}>{filterButtons}</div>
        </>
      </DashBoardItem>

      {studentsList}
    </div>
  );
}
