import React, { useState, useEffect } from 'react';
import { attendanceSheetApi } from '../../api/attendanceSheetApi';
import store from '../../store/store';
import { useSelector } from 'react-redux';

export default function AttendanceSheet() {
  const [sheetData, setSheetData] = useState([]);

  const courseId = useSelector((state) => state.auth.user.courseId);
  const memberId = useSelector((state) => state.auth.user.memberId);

  useEffect(() => {
    const fetchData = async () => {
      const response = await attendanceSheetApi.getStudentAttendanceSheet(courseId, memberId);
      setSheetData(response.data);
    };
    fetchData();
  }, [courseId, memberId]);
  console.log(sheetData);

  return (
    <table border="1" style={{ borderCollapse: 'collapse', width: '100%' }}>
      <thead>
        <tr>
          <th>회차</th>
          <th>날짜</th>
          <th>출석 상태</th>
        </tr>
      </thead>
      <tbody>
        {sheetData.map((item, idx) => (
          <tr key={idx}>
            <td>{item['회차']}</td>
            <td>{item['날짜']}</td>
            <td>{item['출석 상태']}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
