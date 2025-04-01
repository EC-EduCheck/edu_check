import React, { useState, useEffect } from 'react';
import { attendanceSheetApi } from '../../api/attendanceSheetApi';
import { useSelector } from 'react-redux';
import styles from './AttendanceSheet.module.css';

export default function AttendanceSheet() {
  const [sheetData, setSheetData] = useState([]);

  const courseId = useSelector((state) => state.auth.user.courseId);
  const courseName = useSelector((state) => state.auth.user.courseName);
  const memberId = useSelector((state) => state.auth.user.memberId);
  const name = useSelector((state) => state.auth.user.name);

  useEffect(() => {
    const fetchData = async () => {
      const response = await attendanceSheetApi.getStudentAttendanceSheet(courseId, memberId);
      setSheetData(response.data);
    };
    courseId && memberId && fetchData();
  }, [courseId, memberId, name, courseName]);
  console.log(sheetData);

  return (
    <div className={styles.wrapper}>
      <div className={styles.buttonContainer}>
        <button className={styles.button} onClick={null}>다운로드</button>
        <button className={styles.button} onClick={null}>x</button>
      </div>
      <table className={styles.table}>
        <thead className={styles.thead}>
          <tr>
            <th className={styles.th}>회차</th>
            <th className={styles.th}>날짜</th>
            <th className={styles.th}>출석 상태</th>
          </tr>
        </thead>
        <tbody>
          {sheetData.map((item, idx) => (
            <tr key={idx}>
              <td className={styles.td}>{item['회차']}</td>
              <td className={styles.td}>{item['날짜']}</td>
              <td className={styles.td}>{item['출석 상태']}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
