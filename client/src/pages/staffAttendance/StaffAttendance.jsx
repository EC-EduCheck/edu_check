import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import styles from './StaffAttendance.module.css';
import { attendanceApi } from '../../api/attendanceApi';

import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import FilterButton from '../../components/buttons/filterButton/FilterButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';

export default function StaffAttendance() {
  const [isActiveIndex, setIsActiveIndex] = useState(false);
  const [dataList, setDataList] = useState([]);
  const [students, setStudents] = useState([]);
  const { courseId } = useSelector((state) => state.auth.user);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalData, setModalData] = useState({
    content: '페이지를 확인할 수 없습니다.',
  });

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
      setStudents(response.data.data.students);
    } catch (error) {
      const modalData = {
        content: error.message,
      };

      setModalData(modalData);
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
      EARLY_LEAVE: '조퇴',
      LATE: '지각',
      null: '결석',
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
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} {...modalData}></Modal>
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
