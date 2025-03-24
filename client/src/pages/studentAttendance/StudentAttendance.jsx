import React from 'react'
import styles from "./StudentAttendance.module.css"
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import DataBoard from '../../components/dataBoard/DataBoard';

export default function StudentAttendance() {
  const attendanceRatio = 56;
  const lateCount = 3;
  const earlyLeaveCount = 0;
  const absenceCount = 0;
  const cumulativeAbsenceCount = 1;
  return (
    <>
      <div className={styles.dashBoardItemDiv}>
        <div className={styles.attendanceStatistics}>
          <div className={styles.attendanceRatio}>
            <DashBoardItem width={'100%'}>
              <p className={styles.headingText}>출석률 {attendanceRatio}%</p>
            </DashBoardItem>
          </div>
          <div className={styles.attendanceCurrent}>
            <DashBoardItem width={'100%'}>
              <p className={styles.headingText}>결석 현황</p>
              <div className={styles.attendanceType}>
                <DataBoard title="지각" data={`${lateCount}회`}></DataBoard>
                <DataBoard title="조퇴" data={`${earlyLeaveCount}회`}></DataBoard>
                <DataBoard title="결석" data={`${absenceCount}회`}></DataBoard>
                <DataBoard title="누적 결석" data={`${cumulativeAbsenceCount}회`}></DataBoard>
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
