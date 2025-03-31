import React, { useState } from 'react';
import styles from './StaffStudentManage.module.css';
import MainButton from '../../components/buttons/mainButton/MainButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';

export default function StaffStudentManage() {
  const [openModal, setOpenModal] = useState(false);
  const [students, setStudents] = useState([
    {
      name: '홍길동',
      email: 'educheck@example.com',
      phone: '010-1234-1234',
      tagTitle: '수강중',
    },
    {
      name: '홍길동',
      email: 'educheck@example.com',
      phone: '010-1234-1234',
      tagTitle: '수료',
    },
    {
      name: '홍길동',
      email: 'educheck@example.com',
      phone: '010-1234-1234',
      tagTitle: '수강 중단',
    },
  ]);

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
            key={index}
            content={student.name}
            phone={student.phone}
            email={student.email}
            tagTitle={student.tagTitle}
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
