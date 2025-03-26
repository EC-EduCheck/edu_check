import React from 'react'
import styles from "./StudentAttendance.module.css"
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import DataBoard from '../../components/dataBoard/DataBoard';
import ProgressBar from '../../components/progressBar/ProgressBar';
import Calendar from '../../components/calendar/Calendar';
export default function StudentAttendance() {
  const attendanceRatio = 56;
  const lateCount = 3;
  const earlyLeaveCount = 0;
  const absenceCount = 0;
  const cumulativeAbsenceCount = 1;
  const value = 84;
  const startDate = '2024-10-07';
  const endDate = '2025-04-04';
  const attendanceData = [
    { date: '2025-03-04', status: 'ABSENCE' },
    { date: '2025-03-05', status: 'EARlY_LEAVE' },
    { date: '2025-03-06', status: 'LATE' },
    { date: '2025-03-07', status: 'ABSENCE' },
    { date: '2025-03-10', status: 'ABSENCE' },
    { date: '2025-03-13', status: 'LATE' },
    { date: '2025-03-17', status: 'ABSENCE' },
    { date: '2025-03-20', status: 'LATE' },
    { date: '2025-03-21', status: 'LATE' },
    { date: '2025-03-19', status: 'EARlY_LEAVE' },
    { date: '2025-03-25', status: 'EARlY_LEAVE' },
    { date: '2025-03-28', status: 'EARlY_LEAVE' },
    { date: '2025-03-14', status: 'EARlY_LEAVE' },
  ];
  return (
    <>
      <div className={styles.dashBoardItemDiv}>
        <div className={styles.attendanceStatistics}>
          <DashBoardItem width="100%">
            <p className="subTitle">출석률 {attendanceRatio}%</p>
            <div className={styles.progressBarBottom}>
              <ProgressBar
                value={value}
                max={100}
                startDate={startDate}
                endDate={endDate}
              ></ProgressBar>
            </div>
          </DashBoardItem>

          <DashBoardItem width="100%">
            <p className="subTitle">결석 현황</p>
            <div className={styles.attendanceType}>
              <DataBoard title="지각" data={`${lateCount}회`}></DataBoard>
              <DataBoard title="조퇴" data={`${earlyLeaveCount}회`}></DataBoard>
              <DataBoard title="결석" data={`${absenceCount}회`}></DataBoard>
              <DataBoard title="누적 결석" data={`${cumulativeAbsenceCount}회`}></DataBoard>
            </div>
          </DashBoardItem>
        </div>

        <div className={styles.attendanceCalendar}>
          <DashBoardItem width={'100%'}>
            <div className={styles.legendContainer}>
              <div className={styles.legend}>
                <div className={styles.legendItem}>
                  <span className={styles.lateIndicator}></span>
                  <span>지각</span>
                </div>
                <div className={styles.legendItem}>
                  <span className={styles.earlyLeaveIndicator}></span>
                  <span>조퇴</span>
                </div>
                <div className={styles.legendItem}>
                  <span className={styles.absentIndicator}></span>
                  <span>결석</span>
                </div>
              </div>
            </div>
            <div className={styles.calendarWrapper}>
              <Calendar attendanceData={attendanceData}></Calendar>
            </div>
          </DashBoardItem>
        </div>
      </div>
    </>
  );
}
