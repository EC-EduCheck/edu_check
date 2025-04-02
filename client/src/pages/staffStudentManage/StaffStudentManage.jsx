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

  const [birthday, setBirthday] = useState({
    year: '',
    month: '',
    day: '',
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
        console.log(response.data.data.students);
        setStudents((prev) => [...prev, ...response.data.data.students]);
      } catch (error) {
        console.error(error);
      }
    };
    fetchStudents();
  }, []);

  const [errors, setErrors] = useState({
    name: '',
    email: '',
    phone: '',
  });

  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const phoneRegex = /^01[0-9]-\d{3,4}-\d{4}$/;
  const BIRTHDAY_YEAR_LIST = Array.from({ length: 50 }, (_, i) => `${i + 1980}`);
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

    if (name === 'email') {
      if (!emailRegex.test(value)) {
        errorMessage = '유효한 이메일 주소를 입력해주세요.';
      }
    }

    if (name === 'phone') {
      if (!phoneRegex.test(value)) {
        errorMessage = '유효한 전화번호 형식(예: 010-1234-1234)을 입력해주세요.';
      }
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

  const handleTagChange = (index, newTagTitle) => {
    setStudents((prevStudents) =>
      prevStudents.map((student, i) =>
        i === index ? { ...student, tagTitle: newTagTitle } : student,
      ),
    );
  };

  const openModalHandler = () => setOpenModal(true);

  const closeModalHandler = () => setOpenModal(false);

  // const handleChange = (e) => {
  //   const { name, value } = e.target;
  //   console.log(value);
  //   console.log(name);

  //   setNewStudent((preState) => ({
  //     ...preState,
  //     [name]: value,
  //   }));
  // };

  const handleSubmit = async (e) => {
    e.preventDefault();

  };

  const handleBirthdayChange = (e) => {
    const { name, value } = e.target;

    setBirthday((prev) => {
      const updatedBirthday = { ...prev, [name]: value };

      if (updatedBirthday.year && updatedBirthday.month) {
        const year = parseInt(updatedBirthday.year, 10);
        const month = parseInt(updatedBirthday.month, 10);

        if (!isNaN(year) && !isNaN(month)) {
          const maxDays = getDaysInMonth(new Date(year, month - 1));
          if (updatedBirthday.day > maxDays) {
            updatedBirthday.day = maxDays.toString();
          }
        }
      }
      return updatedBirthday;
    });
  };

  const inputBox = (
    <>
      <form onSubmit={handleSubmit}>
        <div className={styles.inputContainer}>
          <label>이름</label>
          <input
            className={styles.smallInputBox}
            name="name"
            type="text"
            placeholder="홍길동"
            onChange={handleChange}
            content=""
          ></input>
          <div className={styles.regexFont}>
            {errors.name && <p style={{ color: 'red' }}>{errors.name}</p>}
          </div>

          <label>연락처</label>
          <input
            className={styles.smallInputBox}
            name="phone"
            type="text"
            placeholder="010-0000-0000."
            onChange={handleChange}
          ></input>
          <div className={styles.regexFont}>
            {errors.phone && <p style={{ color: 'red' }}>{errors.phone}</p>}
          </div>

          <label>생년월일</label>
          <div class="info" id="info__birth" className={styles.birthdate}>
            <select className={styles.smallInputBox} name="year" onChange={handleBirthdayChange}>
              {BIRTHDAY_YEAR_LIST.map((year, index) => (
                <option key={index} value={year}>
                  {year}
                </option>
              ))}
            </select>
            <select className={styles.smallInputBox} name="month" onChange={handleBirthdayChange}>
              {BIRTHDAY_MONTH_LIST.map((month, index) => (
                <option key={index} value={month}>
                  {month}
                </option>
              ))}
            </select>
            <select className={styles.smallInputBox} name="day" onChange={handleBirthdayChange}>
              {BIRTHDAY_DAY_LIST.map((day, index) => (
                <option key={index} value={day}>
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
          ></input>
          <div className={styles.regexFont}>
            {errors.email && <p style={{ color: 'red' }}>{errors.email}</p>}
          </div>
        </div>
        <div className={styles.MainButton}>
          <button type="submit" className={styles.button}>
            등록
          </button>
        </div>
      </form>
    </>
  );

  return (
    <>
      <div>
        <MainButton title="학습자 등록" handleClick={openModalHandler} isEnable={true}></MainButton>
      </div>

      <div className={styles.studentsBox}>
        {students.map((student, index) => (
          <BaseListItem
            key={student.memberId}
            content={student.studentName}
            phone={student.studentPhoneNumber}
            email={student.studentEmail}
            tagTitle={statusMap[student.registrationStatus] || ' '}
            onTagChange={(newTagTitle) => handleTagChange(index, newTagTitle)}
          />
        ))}
      </div>

      <div>
        <Modal
          // mainText="등록"
          // mainClick={handleClick}
          content={inputBox}
          isOpen={openModal}
          onClose={closeModalHandler}
        ></Modal>
      </div>
    </>
  );
}
