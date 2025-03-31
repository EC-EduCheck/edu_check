import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import styles from './StudentAttendance.module.css';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import DataBoard from '../../components/dataBoard/DataBoard';
import ProgressBar from '../../components/progressBar/ProgressBar';
import Calendar from '../../components/calendar/Calendar';
import { attendanceApi } from '../../api/attendanceApi';

export default function StudentAttendance() {
  const courseId = useSelector((state) => state.auth.user.courseId);

  const [attendanceStats, setAttendanceStats] = useState({
    attendanceRate: 0,
    lateCount: 0,
    earlyLeaveCount: 0,
    absentCount: 0,
    accumulatedAbsence: 0,
  });

  const [courseInfo, setCourseInfo] = useState({
    name: '',
    startDate: '',
    endDate: '',
  });

  const [attendanceData, setAttendanceData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth() + 1;

  useEffect(() => {
    const fetchAttendanceStats = async () => {
      try {
        console.log('통계 API 호출 시작:', courseId);
        setIsLoading(true);
        const response = await attendanceApi.getAbsenceAttendanceAndRate(courseId);
        console.log('통계 API 응답:', response);

        if (response && response.data) {
          setAttendanceStats({
            attendanceRate: response.data.attendanceRate || 0,
            lateCount: response.data.lateCount || 0,
            earlyLeaveCount: response.data.earlyLeaveCount || 0,
            absentCount: response.data.absentCount || 0,
            accumulatedAbsence: response.data.accumulatedAbsence || 0,
          });
        }
      } catch (err) {
        console.error('출석 통계 데이터를 가져오는 중 오류 발생:', err);
        setError('출석 통계를 불러오는 데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    if (courseId) {
      fetchAttendanceStats();
    } else {
      console.warn('courseId가 없습니다. Redux store를 확인하세요.');
      setIsLoading(false);
    }
  }, [courseId]);

  useEffect(() => {
    const fetchAttendanceRecords = async () => {
      try {
        console.log('출석 기록 API 호출 시작:', courseId);
        setIsLoading(true);

        const response = await attendanceApi.getAttendanceRecords(courseId);
        console.log('출석 기록 API 응답:', response);

        if (response && response.data) {
          setCourseInfo({
            name: response.data.courseName,
            startDate: response.data.startDate,
            endDate: response.data.endDate,
          });

          const formattedData = response.data.attendanceList.map((item) => ({
            date: item.lectureDate,
            status: item.attendanceStatus,
          }));

          setAttendanceData(formattedData);
        }
      } catch (err) {
        console.error('출석 기록 데이터를 가져오는 중 오류 발생:', err);
        console.error('에러 상세 정보:', err.response?.data || err);

        try {
          console.log('대체 방법으로 출석 기록 조회 시도');
          const response = await attendanceApi.getAttendanceRecords(courseId, 0, 100); // 페이지 크기를 크게 설정

          if (response && response.data) {
            setCourseInfo({
              name: response.data.courseName,
              startDate: response.data.startDate,
              endDate: response.data.endDate,
            });

            const formattedData = response.data.attendanceList.map((item) => ({
              date: item.lectureDate,
              status: item.attendanceStatus,
            }));

            setAttendanceData(formattedData);
          }
        } catch (fallbackErr) {
          console.error('대체 방법도 실패:', fallbackErr);
          setError('출석 기록을 불러오는 데 실패했습니다.');
        }
      } finally {
        setIsLoading(false);
      }
    };

    if (courseId) {
      fetchAttendanceRecords();
    }
  }, [courseId]);

  if (!courseId) return <div>코스 정보를 불러올 수 없습니다.</div>;
  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>{error}</div>;

  return (
    <>
      <div className={styles.dashBoardItemDiv}>
        <div className={styles.attendanceStatistics}>
          <DashBoardItem width="100%">
            <p className="subTitle">출석률 {attendanceStats.attendanceRate}%</p>
            <div className={styles.progressBarBottom}>
              <ProgressBar
                value={attendanceStats.attendanceRate}
                max={100}
                startDate={courseInfo.startDate}
                endDate={courseInfo.endDate}
              ></ProgressBar>
            </div>
          </DashBoardItem>

          <DashBoardItem width="100%">
            <p className="subTitle">결석 현황</p>
            <div className={styles.attendanceType}>
              <DataBoard title="지각" data={`${attendanceStats.lateCount}회`}></DataBoard>
              <DataBoard title="조퇴" data={`${attendanceStats.earlyLeaveCount}회`}></DataBoard>
              <DataBoard title="결석" data={`${attendanceStats.absentCount}회`}></DataBoard>
              <DataBoard
                title="누적 결석"
                data={`${attendanceStats.accumulatedAbsence}회`}
              ></DataBoard>
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
