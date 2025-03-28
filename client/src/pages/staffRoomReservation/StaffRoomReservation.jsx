import React, { useState } from 'react'
import styles from "./StaffRoomReservation.module.css"
import DashBoardItem from "../../components/dashBoardItem/DashBoardItem"
import Modal from '../../components/modal/Modal';
import MainButton from '../../components/buttons/mainButton/MainButton';
import InfoItem from '../../components/infoItem/InfoItem'

export default function StaffRoomReservation() {
  const [openModal, setOpenModal] = useState(false);

  const openModalHandler = () => setOpenModal(true);
  const closeModalHandler = () => setOpenModal(false);

  const reservation = [
    {
      course: '자바 풀스텍 웹개발 교육과정',
      name: '과정 관리자',
      reservationTime: '14:10 ~ 16:00',
      room:'회의실1',
    }
  ]

  const inputBox = (
    <>
    <div className={styles.inputContainer}>
      <label>예약 상세 정보</label>
      
      {reservation.map((item, index) => (
        <div key={index} className={styles.infoItem}>
          <InfoItem title="교육과정명" content={item.course} />
          <InfoItem title="예약자" content={item.name} />
          <InfoItem title="예약 시간" content={item.reservationTime} />
          <InfoItem title="회의실" content={item.room} />
        </div>
      ))}

      <div className={styles.mainButton}>
        <MainButton title="예약 취소"></MainButton>
      </div>
    </div>
    </>
    );

  return (
    <div className={styles.roomDashBoardItem}>
      <DashBoardItem title="예약 현황"></DashBoardItem>
      <MainButton title="임시버튼"handleClick={openModalHandler} isEnable={true}></MainButton>

      <Modal
      content={inputBox}
      isOpen={openModal}
      onClose={closeModalHandler}
      >  
      </Modal>
    </div>
  )
}
