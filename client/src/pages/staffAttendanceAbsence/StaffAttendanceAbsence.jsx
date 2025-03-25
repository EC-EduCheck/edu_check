import React, { useEffect, useState } from 'react';
import styles from './StaffAttendanceAbsence.module.css';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';

export default function StaffAttendanceAbsence() {
  const [data, setData] = useState();
  useEffect(() => {
    async function fetchData() {
      try {
        const response = await absenceAttendancesApi.getAbsenceAttendancesByCourseId(1);
        setData(response.data);
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
    console.log('data=' + data);
  }, []);

  return <div>StaffAttendanceAbsence</div>;
}
