import React from 'react'
import styles from "./StudentAttendance.module.css"
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import DataBoard from '../../components/dataBoard/DataBoard';

export default function StudentAttendance() {
  return (
    <>
      <div className={styles.dashBoardItemDiv}>
        <div className={styles.attendanceStatistics}>
          <div className={styles.attendanceRatio}>
            <DashBoardItem width={'100%'}>
            <p>출석률 56%</p>
            </DashBoardItem>
          </div>
          <div className={styles.attendanceCurrent}>
            <DashBoardItem width={'100%'}>
              <p>결석 현황</p>
              <div className={styles.AttendanceType}>
                <DataBoard title="지각" data="0회"></DataBoard>
                <DataBoard title="조퇴" data="0회"></DataBoard>
                <DataBoard title="결석" data="0회"></DataBoard>
                <DataBoard title="누적 결석" data="0회"></DataBoard>
              </div>
            </DashBoardItem>
          </div>
        </div>
        <div className={styles.attendanceCalendar}>
          <DashBoardItem width={'100%'}></DashBoardItem>
        </div>
      </div>
    </>
  );
}
