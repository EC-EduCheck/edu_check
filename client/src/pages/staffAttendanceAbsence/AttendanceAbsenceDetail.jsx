import React, { useState, useEffect } from 'react';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';
import styles from './AttendanceAbsenceDetail.module.css';
import InputBox from '../../components/inputBox/InputBox';

export default function AttendanceAbsenceDetail({ courseId, id }) {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  const categoryMapper = {
    ABSENCE: '결석',
    LATE: '지각',
    EARLY_LEAVE: '조퇴',
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await absenceAttendancesApi.getAbsenceAttendance(courseId, id);
        setData(response.data.data);
      } catch (err) {
        console.error('상세 데이터를 가져오는 중 오류 발생:', err);
        setError(err);
      }
    };

    fetchData();
  }, [courseId, id]);

  if (error) return <div>오류가 발생했습니다.</div>;

  if (!data) return <div>로딩 중...</div>;

  return (
    <div className={styles.detail}>
      <h2>{categoryMapper[data.category]}</h2>
      <div>신청자</div>
      <div className={styles.boxItem}>{data.absenceAttendanceRequesterName}</div>
      <div>기간</div>
      <div className={styles.boxItem}>
        {data.startDate}
        {data.startDate !== data.endDate && ` ~ ${data.endDate}`}
      </div>
      <div>서류</div>
      {data.files && data.files?.length ? (
        data.files.map((file, index) => (
          <div key={index} className={styles.boxItem}>
            <a
              href={file.fileUrl}
              target="_blank"
              rel="noopener noreferrer"
              className={styles.fileName}
              title={file.originalName}
            >
              {file.originalName}
            </a>
          </div>
        ))
      ) : (
        <div className={styles.boxItem}>첨부 파일이 없습니다.</div>
      )}
      <div>사유</div>
      <InputBox value = {data.reason}/>
    </div>
  );
}
