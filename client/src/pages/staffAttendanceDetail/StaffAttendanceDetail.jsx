import React, { useEffect, useState } from 'react';
import styles from './StaffAttendanceDetail.module.css';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import DataBoard from '../../components/dataBoard/DataBoard';
import { attendanceApi } from '../../api/attendanceApi';
import { useParams } from 'react-router-dom';

export default function StaffAttendanceDetail() {
  const { courseId, studentId } = useParams();
  const [studentAttendance, setStudentAttendance] = useState({
    studentName: '',
    studentPhoneNumber: '',
    attendanceRecordList: [],
    statistics: {},
  });

  useEffect(() => {
    const studentAttendanceById = async () => {
      try {
        const response = await attendanceApi.getStudentAttendances(courseId, studentId);
        const studentAttendanceData = response.data.data;
        console.log('세부 출결조회', studentAttendanceData);
        setStudentAttendance(studentAttendanceData);
      } catch (error) {
        console.error('수강생 출결 현황 조회 실패:', error);
      }
    };

    studentAttendanceById();
  }, [courseId, studentId]);

  return (
    <div className={styles.container}>
      <div className={styles.dashboardContainer}>
        <DashBoardItem>
          <div className={styles.userInfoContainer}>
            <div className={styles.userInfo}>
              <h2 className={styles.userName}>{studentAttendance.studentName}</h2>
              <p className={styles.userPhone}>{studentAttendance.studentPhoneNumber}</p>
            </div>
          </div>
          <div className={styles.databoardContainer}>
            <DataBoard
              title="금일 기준 출석률"
              data={`${Math.round(studentAttendance.statistics.attendanceRateUntilToday) || 0}%`}
            />
            <DataBoard
              title="전체 출석률"
              data={`${Math.round(studentAttendance.statistics.totalAttendanceRate) || 0}%`}
            />
            <DataBoard
              title="과정 진행률"
              data={`${Math.round(studentAttendance.statistics.courseProgressRate) || 0}%`}
            />
          </div>
        </DashBoardItem>
      </div>

      <div className={styles.contentWrapper}>
        <div className={styles.listContainer}>
          {studentAttendance.attendanceRecordList &&
            studentAttendance.attendanceRecordList.map((item, index) => (
              <BaseListItem
                key={index}
                content={item.lectureDateTime}
                tagTitle={getAttendanceStatusText(item.attendanceStatus)}
              />
            ))}
        </div>
      </div>
    </div>
  );
}

// 출석 상태 텍스트 변환 함수
function getAttendanceStatusText(status) {
  const statusMap = {
    ATTENDANCE: '출석',
    LATE: '지각',
    EARLY_LEAVE: '조퇴',
    ABSENT: '결석',
  };
  return statusMap[status] || status;
}
