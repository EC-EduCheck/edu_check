import React, { useEffect } from 'react'
import { attendanceApi } from '../api/attendanceApi'
import axios from 'axios'

export default function Test() {
  useEffect(() => {
    const test = async () => {
      // const response = await attendanceApi.getStudentAttendanceSheet(10, 1);
      const response = await axios.get(
        `/courses/10/members/1`,
      {baseURL: import.meta.env.VITE_APP_URL},
      );
      console.log(response);
      
    }
    test();
  })

  return (
    <div>Test</div>
  )
}
