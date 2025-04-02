import React, { useEffect, useState } from 'react';
import styles from './StaffStudentManage.module.css';
import MainButton from '../../components/buttons/mainButton/MainButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';
import { useSelector } from 'react-redux';
import { studentManageApi } from '../../api/studentManageApi';
import axios from 'axios';

export default function StaffStudentManage() {
  const courseId = useSelector((state) => state.auth.user.courseId);
  const [openModal, setOpenModal] = useState(false);
  const [students, setStudents] = useState([]);
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

  const [newStudent, setNewStudent] = useState({
    name: '',
    email: '',
    phone: '',
    tagTitle: '수강중',
  });

  const [errors, setErrors] = useState({
    name: '',
    email: '',
    phone: '',
  });

  const nameRegex = /^[가-힣]+$/;
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const phoneRegex = /^01[0-9]-\d{3,4}-\d{4}$/;
  const BIRTHDAY_YEAR_LIST = Array.from({ length: 50 }, (_, i) => `${i + 1980}년`);
  const BIRTHDAY_MONTH_LIST = Array.from({ length: 12 }, (_, i) => `${i + 1}월`);
  const BIRTHDAY_DAY_LIST = Array.from({ length: 31 }, (_, i) => `${i + 1}일`);

  const handleChange = (e) => {
    const { name, value } = e.target;

    let errorMessage = '';

    if (name === 'name') {
      if (!nameRegex.test(value)) {
        errorMessage = '이름은 한글만 입력 가능합니다.';
      }
    }

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
    console.log(newStudent);
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
            placeholder="이름을 입력해주세요."
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
            placeholder="연락처를 입력해주세요."
            onChange={handleChange}
          ></input>
          <div className={styles.regexFont}>
            {errors.phone && <p style={{ color: 'red' }}>{errors.phone}</p>}
          </div>

          <label>생년월일</label>
          <div class="info" id="info__birth" className={styles.birthdate}>
            <select className={styles.smallInputBox}>
              {BIRTHDAY_YEAR_LIST.map((year, index) => (
                <option key={index}>{year}</option>
              ))}
            </select>
            <select className={styles.smallInputBox}>
              {BIRTHDAY_MONTH_LIST.map((month, index) => (
                <option key={index}>{month}</option>
              ))}
            </select>
            <select className={styles.smallInputBox}>
              {BIRTHDAY_DAY_LIST.map((day, index) => (
                <option key={index}>{day}</option>
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
