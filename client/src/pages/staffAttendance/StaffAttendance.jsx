import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import styles from './StaffAttendance.module.css';
import { attendanceApi } from '../../api/attendanceApi';

import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import FilterButton from '../../components/buttons/filterButton/FilterButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';
import { useNavigate } from 'react-router-dom';
import { URL_PATHS } from '../../constants/urlPaths';
export default function StaffAttendance() {
  const navigate = useNavigate();
  const [dataList, setDataList] = useState([]);
  const isMultiSelect = true;
  const [isActiveIndex, setIsActiveIndex] = useState(
    isMultiSelect ? dataList.map((_, index) => index) : false,
  );
  const [students, setStudents] = useState([]);
  const { courseId } = useSelector((state) => state.auth.user);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalData, setModalData] = useState({
    content: '페이지를 확인할 수 없습니다.',
  });

  useEffect(() => {
    if (isMultiSelect && dataList.length > 0) {
      setIsActiveIndex(dataList.map((_, index) => index));
    }
  }, [dataList, isMultiSelect]);

  const getAttendances = async () => {
    try {
      const response = await attendanceApi.getTodayAttendances(courseId);
      const { totalAttendance, totalEarlyLeave, totalLate, totalAbsence } =
        response.data.data.summary;
      setDataList([
        { label: '출석', value: totalAttendance },
        { label: '조퇴', value: totalEarlyLeave },
        { label: '지각', value: totalLate },
        { label: '결석', value: totalAbsence },
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
    if (courseId) {
      getAttendances();
    }
  }, [courseId]);

  const handleActiveFilter = (index) => {
    setIsActiveIndex((prev) => {
      if (isMultiSelect) {
        return prev.includes(index) ? prev.filter((i) => i !== index) : [...prev, index];
      } else {
        return prev === index ? false : index;
      }
    });
  };
  const handleStudentClick = (studentId) => {
    navigate(URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.DETAIL(courseId, studentId));
  };

  const filterButtons = dataList.map((item, index) => {
    return (
      <FilterButton
        key={index}
        index={index}
        isActiveIndex={isActiveIndex}
        isMultiSelect={true}
        title={item.label}
        content={item.value}
        handleActiveFilter={handleActiveFilter}
      ></FilterButton>
    );
  });

  const studentsList = students
    .map((item, index) => {
      const tag = {
        ATTENDANCE: '출석',
        EARLY_LEAVE: '조퇴',
        LATE: '지각',
        ABSENT: '결석',
      };

      const isFiltered =
        (Array.isArray(isActiveIndex) &&
          isActiveIndex.some((idx) => dataList[idx]?.label === tag[item.status])) ||
        isActiveIndex === false;

      if (isFiltered) {
        return (
          <div key={index} className={styles.baseListItems}>
            <BaseListItem
              content={item.studentName}
              tagTitle={tag[item.status]}
              onClick={() => handleStudentClick(item.studentId)}
            />
          </div>
        );
      }
      return null;
    })
    .filter(Boolean);

  return (
    <div className={styles.container}>
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} {...modalData}></Modal>
      <div className={styles.dashBoardItemBox}>
        <DashBoardItem width="100%">
          <>
            <h2 className="subTitle">출결 현황</h2>
            <div className={styles.filterButtonBox}>{filterButtons}</div>
          </>
        </DashBoardItem>
      </div>

      <div className={styles.studentsBox}>{studentsList}</div>
    </div>
  );
}
