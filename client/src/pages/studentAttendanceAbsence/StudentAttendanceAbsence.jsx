import React, { useState } from 'react';
import styles from './StudentAttendanceAbsence.module.css';
import LeftLineListItem from '../../components/listItem/leftLineListItem/LeftLineListItem';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import RoundButton from '../../components/buttons/roundButton/RoundButton';
import MainButton from '../../components/buttons/mainButton/MainButton';
import NewInputBox from '../../components/inputBox/newInputBox/NewInputBox';
import Modal from '../../components/modal/Modal';

export default function StudentAttendanceAbsence() {
  const [AbsenceListData, setAbsenceListData] = useState(false);
  const [students, setStudents] = useState([
      {
        absenceAttendanceId: 4,
        startDate: '2025-03-14',
        endDate: '2025-03-14',
        category: 'LATE',
        isApprove: '대기',
        approvedDate: '2025-03-14',
      },
  ]);
  const [openModal, setOpenModal] = useState(false);

  const handleTagChange = () => {
    setOpenModal(true);
  }

  const handleCloseModal = () => {
    setOpenModal(false);
  }

  const inputBox = (
    <>
          <RoundButton title="결석"/>
          <RoundButton title="조퇴"/>
          <RoundButton title="지각"/>
          <div className={styles.inputContainer}>
            <label>신청날짜</label>
            <input className={styles.smallInputBox} placeholder="2025.03.31"></input>
            <label>신청기간</label>
            <input className={styles.smallInputBox} placeholder="2025.03.31-2025.04.01"></input>
            <label>서류</label>
            <input className={styles.smallInputBox} placeholder="파일을 첨부해주세요."></input>
            <label>사유</label>
            <input className={styles.smallInputBox} placeholder="자세한 사유을 입력해주세요."></input>
          </div>  
      </>
    );
  // 데이터 예시
  const response = [
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'LATE',
      isApprove: '대기',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 20,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'EARLY_LEAVE',
      isApprove: '승인',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'ABSENCE',
      isApprove: '승인',
      approvedDate: '2025-03-14',
    },
    {
      absenceAttendanceId: 3,
      startDate: '2025-03-14',
      endDate: '2025-03-14',
      category: 'ABSENCE',
      isApprove: '반려',
      approvedDate: '2025-03-14',
    },
  ];


  const fetchAbsenceList = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 2000);
    });
  };

  const clickMainButton = async () => {
    try {
      const fetchedData = await fetchAbsenceList(); 
      setAbsenceListData(fetchedData);
    } catch (error) {
      console.error('데이터를 불러오는 중 오류가 발생했습니다:', error);
    }
  };



  const absenceList = response.map((item, index) => {
    return (
      <LeftLineListItem
        key={index}
        isClickable={false}
        status={item.isApprove}
        children={item}
        onTagChange={handleTagChange}
      ></LeftLineListItem>
    );
  });



  const list = ['결석', '조퇴', '지각'];

  const [isActiveIndex, setIsActiveIndex] = useState(0);
  const handleActiveFilter = (index) => {
    setIsActiveIndex(index);
  };

  const roundButtons = list.map((item, index) => {
    return (
      <RoundButton
        key={index}
        index={index}
        isActiveIndex={isActiveIndex}
        title={item}
        handleActiveFilter={handleActiveFilter}
      ></RoundButton>
    );
  });

  return (
    <>
      <div className={styles.LeftLineListItemDisplay}>
        <div className={styles.absenceLeftLineItem}>
          <p className="subTitle">신청 내역</p>
          {absenceList}
        </div>

        <div className={styles.absenceDashBoardItem}>
          <p className="subTitle">유고 결석 내역</p>
          <DashBoardItem>
            <div style={{ display: 'flex', width: '100%' }}>{roundButtons}</div>

            <div className={styles.InputBox}>
              <NewInputBox label="기간" title="날짜 선택하기" />
              <NewInputBox label="서류" title="파일 선택 또는 끌어놓기..." />
              <NewInputBox label="사유" />
            </div>

            <div className={styles.mainButton}>
              <MainButton title="신청" handleClick={clickMainButton} />
            </div>
          </DashBoardItem>
        </div>
      </div>

    <div>
      <Modal
      isOpen={openModal}
      onClose={handleCloseModal}
      isEnable={true}
      mainClick
      mainText={"수정"}
      content={inputBox}
      ></Modal>
    </div>
    </>
  );
}
