import React, { useState, useEffect } from 'react';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';

export default function AttendanceAbsenceDetail({ id }) {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const response = await absenceAttendancesApi.getAbsenceAttendancesByCourseId(id);
      setData(response.data.data);
    };

    fetchData();
  }, [id]);

  return (
    <>
      <p>신청자</p>
      <div>{data.absenceAttendanceRequesterName}</div>
      <p>기간</p>
      <div>
        {data.startDate} ~ {data.endDate}
      </div>
      <p>서류</p>
      {data.files.map((file, index) => (
        <div key={index}>
          <a href={file.fileUrl} target="_blank">
            {file.originalName}
          </a>
        </div>
      ))}
      <div>{data.files}</div>
    </>
  );
}
