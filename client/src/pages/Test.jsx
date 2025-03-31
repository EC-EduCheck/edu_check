import React, { useEffect } from 'react'
import { attendanceApi } from '../api/attendanceApi'

export default function Test() {
  useEffect(() => {
    const test = async () => {
      const response = await attendanceApi.getStudentAttendanceSheet(10, 1);
      console.log(response);
    }
    test();
  })

  return (
    <div>Test</div>
  )
}
