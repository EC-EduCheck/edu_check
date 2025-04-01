import React, { useRef, useState } from 'react';
import styles from './StudentAttendanceAbsence.module.css';
import LeftLineListItem from '../../components/listItem/leftLineListItem/LeftLineListItem';
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem';
import RoundButton from '../../components/buttons/roundButton/RoundButton';
import MainButton from '../../components/buttons/mainButton/MainButton';
import NewInputBox from '../../components/inputBox/newInputBox/NewInputBox';
import Modal from '../../components/modal/Modal';
import 'react-datepicker/dist/react-datepicker.css';
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/locale';

export default function StudentAttendanceAbsence() {
  const [absenceList, setAbsenceList] = useState([
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
  ]);

  const [openModal, setOpenModal] = useState(false);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [reason, setReason] = useState('');
  const [file, setFile] = useState(null);
  const [currentItem, setCurrentItem] = useState(null);
  const fileInputRef = useRef(null);

  const handleTagChange = (item) => {
    setCurrentItem(item);
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setCurrentItem(null);
  };

  const categoryMap = {
    0: 'ABSENCE',
    1: 'EARLY_LEAVE',
    2: 'LATE',
  };

  const inputBox = (
    <>
      <RoundButton title="결석" />
      <RoundButton title="조퇴" />
      <RoundButton title="지각" />
      <div className={styles.inputContainer}>
        <label>신청날짜</label>
        <input
          className={styles.smallInputBox}
          placeholder="2025.03.31"
          readOnly
          value={new Date().toISOString().split('T')[0].replace(/-/g, '.')}
        ></input>
        <label>신청기간</label>
        <input
          className={styles.smallInputBox}
          placeholder="2025.03.31-2025.04.01"
          readOnly
          value={
            currentItem
              ? `${currentItem.startDate.replace(/-/g, '.')}-${currentItem.endDate.replace(/-/g, '.')}`
              : ''
          }
        ></input>
        <label>서류</label>
        <input className={styles.smallInputBox} placeholder="파일을 첨부해주세요."></input>
        <label>사유</label>
        <input className={styles.smallInputBox} placeholder="자세한 사유을 입력해주세요."></input>
      </div>
    </>
  );

  const handleSubmit = () => {
    if (!startDate || !endDate) {
      alert('시작일과 종료일을 입력해주세요.');
      return;
    }

    const formatDate = (date) => {
      return date
        .toLocaleDateString('ko-KR', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
        })
        .replace(/\. /g, '-')
        .replace(/\./g, '');
    };

    const newItem = {
      absenceAttendanceId: Date.now(),
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      category: categoryMap[isActiveIndex],
      isApprove: '대기',
      approvedDate: formatDate(new Date()),
    };

    setAbsenceList((prevList) => [newItem, ...prevList]);

    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }

    setStartDate(null);
    setEndDate(null);
    setReason('');
    setFile(null);

    alert('신청이 완료되었습니다.');
  };

  const list = ['결석', '조퇴', '지각'];

  const [isActiveIndex, setIsActiveIndex] = useState(0);
  const handleActiveFilter = (index) => {
    setIsActiveIndex(index);
  };

  const roundButtons = list.map((item, index) => (
    <RoundButton
      key={index}
      index={index}
      isActiveIndex={isActiveIndex}
      title={item}
      handleActiveFilter={handleActiveFilter}
    />
  ));

  const absenceListItems = absenceList.map((item, index) => (
    <LeftLineListItem
      key={index}
      isClickable={false}
      status={item.isApprove}
      children={item}
      onTagChange={() => handleTagChange(item)}
    />
  ));

  return (
    <>
      <div className={styles.LeftLineListItemDisplay}>
        <div className={styles.absenceLeftLineListItem}>
          <p className="subTitle">신청 내역</p>
          <div className={styles.absenceAttendanceList}>{absenceListItems}</div>
        </div>

        <div className={styles.absenceDashBoardItem}>
          <p className="subTitle">유고 결석 내역</p>
          <DashBoardItem>
            <div className={styles.InputBox}>
              <div className={styles.categoryButton}>{roundButtons}</div>
              <p className={styles.termTitle}>기간</p>
              <div className={styles.dateInputBox}>
                <DatePicker
                  selected={startDate}
                  onChange={setStartDate}
                  dateFormat="yyyy-MM-dd"
                  className={styles.dateInput}
                  locale={ko}
                  placeholderText="시작일"
                  maxDate={endDate}
                  utcOffset={0}
                />
                <span className={styles.dateSeparator}>~</span>
                <DatePicker
                  selected={endDate}
                  onChange={setEndDate}
                  dateFormat="yyyy-MM-dd"
                  className={styles.dateInput}
                  locale={ko}
                  placeholderText="종료일"
                  minDate={startDate}
                  utcOffset={0}
                />
              </div>

              <div className={styles.textInput}>
                <NewInputBox
                  label="서류"
                  title="파일 선택 또는 끌어놓기..."
                  type="file"
                  onChange={(e) => setFile(e.target.files[0])}
                  innerRef={fileInputRef}
                />
                <div className={styles.reason}>
                  <NewInputBox
                    label="사유"
                    value={reason}
                    onChange={(e) => setReason(e.target.value)}
                  />
                </div>
              </div>
            </div>

            <div className={styles.submitButton}>
              <MainButton isEnable={true} title="신청" handleClick={handleSubmit} />
            </div>
          </DashBoardItem>
        </div>
      </div>

      <Modal
        isOpen={openModal}
        onClose={handleCloseModal}
        isEnable={true}
        mainClick
        mainText={'수정'}
        content={inputBox}
      />
    </>
  );
}
