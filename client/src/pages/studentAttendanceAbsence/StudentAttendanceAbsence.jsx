import React from 'react'
import styles from "./StudentAttendanceAbsence.module.css"
import LeftLineListItem from "../../components/listItem/leftLineListItem/LeftLineListItem"
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem'
import RoundButton from '../../components/buttons/roundButton/RoundButton';
import MainButton from '../../components/buttons/mainButton/MainButton';

export default function StudentAttendanceAbsence() {
  // 데이터 예시
  const response = [
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'LATE',
      isApprove: '대기',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 20,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'EARLY_LEAVE',
      isApprove: '승인',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'ABSENCE',
      isApprove: '승인',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'ABSENCE',
      isApprove: '반려',
      approvedDate: '2025-03-14',
    },
  ];

  const absenceList = response.map((item, index) => {
    return (
      <LeftLineListItem
        key={index}
        isClickable={false}
        status={item.isApprove}
        children={item}
      ></LeftLineListItem>
    );
  });

  return (
    <>
      <p className="subTitle">신청 내역</p>
      {absenceList}
    </>
  );
}
