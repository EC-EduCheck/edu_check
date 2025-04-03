import React, { useEffect, useState } from 'react';
import styles from './StaffStudentManage.module.css';
import MainButton from '../../components/buttons/mainButton/MainButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';
import { useSelector } from 'react-redux';
import { studentManageApi } from '../../api/studentManageApi';
import { getDaysInMonth } from 'date-fns';

export default function StaffStudentManage() {
  const courseId = useSelector((state) => state.auth.user.courseId);
  const [openModal, setOpenModal] = useState(false);
  const [students, setStudents] = useState([]);
  const [newStudent, setNewStudent] = useState({
    name: '',
    phone: '',
    birthDate: '',
    email: '',
    courseId: courseId,
  });
  const [birthday, setBirthday] = useState({
    year: '2000',
    month: '01',
    day: '01',
  });

  const statusMap = {
    PREVIOUS: '등록전',
    PROGRESS: '수강중',
    COMPLETED: '수료',
    DROPPED: '수강 중단',
  };

  useEffect(() => {
    if (!courseId) return;

    const fetchStudents = async () => {
      try {
        const response = await studentManageApi.getStudentList(courseId);
        setStudents(response.data.data.students);
      } catch (error) {
        console.error(error);
      }
    };
    fetchStudents();
  }, [courseId]);

  const [errors, setErrors] = useState({
    name: '',
    email: '',
    phone: '',
  });

  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const phoneRegex = /^01[0-9]-\d{3,4}-\d{4}$/;
  const BIRTHDAY_YEAR_LIST = Array.from({ length: 70 }, (_, i) => `${i + 1960}`);
  const BIRTHDAY_MONTH_LIST = Array.from({ length: 12 }, (_, i) => `${i + 1}`);
  const BIRTHDAY_DAY_LIST =
    birthday.year && birthday.month
      ? Array.from(
          { length: getDaysInMonth(new Date(birthday.year, birthday.month - 1)) },
          (_, i) => `${i + 1}`,
        )
      : [];

  const handleChange = (e) => {
    const { name, value } = e.target;
    let errorMessage = '';

    if (name === 'email' && !emailRegex.test(value)) {
      errorMessage = '유효한 이메일 주소를 입력해주세요.';
    }
    if (name === 'phone' && !phoneRegex.test(value)) {
      errorMessage = '유효한 전화번호 형식(예: 010-1234-1234)을 입력해주세요.';
    }

    setErrors((prevErrors) => ({
      ...prevErrors,
      [name]: errorMessage,
    }));

    setNewStudent((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  useEffect(() => {
    setNewStudent((prev) => ({
      ...prev,
      courseId,
    }));
  }, [courseId]);

  const handleBirthdayChange = (e) => {
    const { name, value } = e.target;

    setBirthday((prev) => {
      const updatedBirthday = { ...prev, [name]: value };

      if (name === 'month') {
        updatedBirthday.day = '';
      }

      return updatedBirthday;
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!birthday.year || !birthday.month || !birthday.day) {
      alert('생년월일 입력은 필수입니다.');
      return;
    }

    try {
      const formattedBirthday = `${birthday.year}-${birthday.month.padStart(2, '0')}-${birthday.day.padStart(2, '0')}`;
      const response = await studentManageApi.registerNewStudent({
        ...newStudent,
        birthDate: formattedBirthday,
      });
      setOpenModal(false);
    } catch (error) {
      console.error(error);
    }
  };

  const inputBox = (
    <form onSubmit={handleSubmit}>
      <div className={styles.inputContainer}>
        <label>이름</label>
        <input
          className={styles.smallInputBox}
          name="name"
          type="text"
          placeholder="홍길동"
          onChange={handleChange}
        />
        {errors.name && (
          <p className={styles.regexFont} style={{ color: 'red' }}>
            {errors.name}
          </p>
        )}

        <label>연락처</label>
        <input
          className={styles.smallInputBox}
          name="phone"
          type="text"
          placeholder="010-0000-0000"
          onChange={handleChange}
        />
        {errors.phone && (
          <p className={styles.regexFont} style={{ color: 'red' }}>
            {errors.phone}
          </p>
        )}

        <label>생년월일</label>
        <div className={styles.birthdate}>
          <select className={styles.smallInputBox} name="year" onChange={handleBirthdayChange}>
            {BIRTHDAY_YEAR_LIST.map((year) => (
              <option key={year} value={year}>
                {year}
              </option>
            ))}
          </select>
          <select className={styles.smallInputBox} name="month" onChange={handleBirthdayChange}>
            {BIRTHDAY_MONTH_LIST.map((month) => (
              <option key={month} value={month}>
                {month}
              </option>
            ))}
          </select>
          <select className={styles.smallInputBox} name="day" onChange={handleBirthdayChange}>
            {BIRTHDAY_DAY_LIST.map((day) => (
              <option key={day} value={day}>
                {day}
              </option>
            ))}
          </select>
        </div>

        <label>이메일</label>
        <input
          className={styles.smallInputBox}
          name="email"
          type="text"
          placeholder="이메일을 입력해주세요."
          onChange={handleChange}
        />
        {errors.email && (
          <p className={styles.regexFont} style={{ color: 'red' }}>
            {errors.email}
          </p>
        )}
      </div>
      <div className={styles.MainButton}>
        <button type="submit" className={styles.button}>
          등록
        </button>
      </div>
    </form>
  );

  return (
    <>
      <div>
        <div className={styles.studentsBox}>
          <div>
            <MainButton
              title="학습자 등록"
              handleClick={() => setOpenModal(true)}
              isEnable={true}
            />
          </div>
          {students.map((student) => (
            <BaseListItem
              key={student.memberId}
              content={student.studentName}
              phone={student.studentPhoneNumber}
              email={student.studentEmail}
              tagTitle={statusMap[student.registrationStatus] || ' '}
              studentId={student.memberId}
              courseId={courseId}
            />
          ))}
        </div>
      </div>
      <Modal content={inputBox} isOpen={openModal} onClose={() => setOpenModal(false)} />
    </>
  );
}
