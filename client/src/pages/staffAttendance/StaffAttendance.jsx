import React, { useEffect, useState } from 'react';
import styles from './StaffAttendance.module.css';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import FilterButton from '../../components/buttons/filterButton/FilterButton';
import { attendanceApi } from '../../api/attendanceApi';
import { useSelector } from 'react-redux';

export default function StaffAttendance() {
  const list = ['출석', '조퇴', '지각', '결석'];
  const [isActiveIndex, setIsActiveIndex] = useState(false);
  const [attendances, setAttendances] = useState([]);
  // TODO : 관리자 로그인 시 courseId 받아오는 로직 추가할 경우 주석 풀기
  // const { courseId } = useSelector((state) => state.auth.user);
  const courseId = 1;

  const getAttendances = async () => {
    const response = await attendanceApi.getTodayAttendances(courseId);
    console.log(response);
    return response;
  };

  useEffect(() => {
    getAttendances();
  }, []);

  // TODO : Click 시 필터링 이벤트 추가
  const handleActiveFilter = (index) => {
    if (index === isActiveIndex) {
      setIsActiveIndex(false);
    } else {
      setIsActiveIndex(index);
    }
  };

  // TODO : API 받아와 content 받아오기
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
