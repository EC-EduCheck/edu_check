import React, { useEffect, useState } from 'react';
import styles from './StaffAttendanceAbsence.module.css';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';
import LeftLineListItem from '../../components/listItem/leftLineListItem/LeftLineListItem';
import { useSelector } from 'react-redux';

export default function StaffAttendanceAbsence() {
  const [data, setData] = useState();
  useEffect(() => {
    async function fetchData() {
      try {
        const response = await absenceAttendancesApi.getAbsenceAttendancesByCourseId(1);
        setData(response.data.data);
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
  }, []);

  const role= useSelector(
    (state) => state.auth.user.role,
  );

  

  return (
    <div>
      {data?.absenceAttendances &&
        data.absenceAttendances.map((item, idx) => (
          <LeftLineListItem
            isClickable={role === 'MIDDLE_ADMIN'}
            status={item.status}
            children={item}
            key={idx}
          />
        ))}
    </div>
  );
}
