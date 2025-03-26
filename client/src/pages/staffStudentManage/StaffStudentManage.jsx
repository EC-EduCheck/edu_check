import React, { useState } from 'react';
import styles from './StaffStudentManage.module.css';
import MainButton from '../../components/buttons/mainButton/MainButton';
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem';
import Modal from '../../components/modal/Modal';

export default function StaffStudentManage() {
  const [openModal, setOpenModal] = useState(false);

  const inputBox = <>
    <div className={styles.inputContainer}>
    <label>이름</label>
    <input className={styles.smallInputBox} placeholder="이름을 입력해주세요."></input>
    <label>연락처</label>
    <input className={styles.smallInputBox} placeholder="연락처를 입력해주세요."></input>
    <label>생년월일</label>
    <div className={styles.birthdate}>
    <input className={styles.smallInputBox} ></input>
    <input className={styles.smallInputBox} ></input>
    <input className={styles.smallInputBox} ></input>
    </div>
    <label>이메일</label>
    <input className={styles.smallInputBox} placeholder="이메일을 입력해주세요."></input>
    </div>
  </>
  return (
    <>
      <div>
        <MainButton title="학습자 등록"></MainButton>
      </div>
      <div>
        <BaseListItem
          content="홍길동"
          phone="010-1234-1234"
          email="educheck@educheck.com"
          tagTitle="수강중"
        ></BaseListItem>
        <BaseListItem
          content="홍길동"
          phone="010-1234-1234"
          email="educheck@educheck.com"
          tagTitle="결석"
        ></BaseListItem>
        <BaseListItem
          content="홍길동"
          phone="010-1234-1234"
          email="educheck@educheck.com"
          tagTitle="수강중"
        ></BaseListItem>
        <BaseListItem
          content="홍길동"
          phone="010-1234-1234"
          email="educheck@educheck.com"
          tagTitle="수료"
        ></BaseListItem>
        <BaseListItem
          content="홍길동"
          phone="010-1234-1234"
          email="educheck@educheck.com"
          tagTitle="수강중"
        ></BaseListItem>
        <Modal mainText="등록" content={inputBox} isOpen={true}>
          <input type="text" />
        </Modal>
      </div>
    </>
  );
}
