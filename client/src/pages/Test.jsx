import React, { useEffect } from 'react';
import { attendanceSheetApi } from '../api/attendanceSheetApi';

export default function Test() {
  useEffect(() => {
    const test = async (courseId, memberId) => {
      const response = await attendanceSheetApi.getStudentAttendanceSheet(courseId, memberId);
      console.log(response);
    };
    test(10,1);
  }, []);

  return <div>Test</div>;
}
