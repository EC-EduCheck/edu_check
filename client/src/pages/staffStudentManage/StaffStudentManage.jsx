import React, { useEffect, useState } from 'react';
import styles from './StaffStudentManage.module.css';
import MainButton from '../../components/buttons/mainButton/MainButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';
import { useSelector } from 'react-redux';
import { studentManageApi } from '../../api/studentManageApi';

export default function StaffStudentManage() {
  const courseId = useSelector((state) => state.auth.user.courseId);
  console.log(courseId);
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

  const handleTagChange = (index, newTagTitle) => {
    setStudents((prevStudents) =>
      prevStudents.map((student, i) =>
        i === index ? { ...student, tagTitle: newTagTitle } : student,
      ),
    );
  };

  const openModalHandler = () => setOpenModal(true);

  const closeModalHandler = () => setOpenModal(false);

  const inputBox = (
    <>
      <div className={styles.inputContainer}>
        <label>이름</label>
        <input className={styles.smallInputBox} placeholder="이름을 입력해주세요."></input>
        <label>연락처</label>
        <input className={styles.smallInputBox} placeholder="연락처를 입력해주세요."></input>
        <label>생년월일</label>
        <div className={styles.birthdate}>
          <input className={styles.smallInputBox}></input>
          <input className={styles.smallInputBox}></input>
          <input className={styles.smallInputBox}></input>
        </div>
        <label>이메일</label>
        <input className={styles.smallInputBox} placeholder="이메일을 입력해주세요."></input>
      </div>
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
          mainText="등록"
          content={inputBox}
          isOpen={openModal}
          onClose={closeModalHandler}
        ></Modal>
      </div>
    </>
  );
}
