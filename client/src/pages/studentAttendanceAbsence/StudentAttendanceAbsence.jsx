import React, { useRef, useState, useEffect } from 'react';
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
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';
import { useSelector } from 'react-redux';

export default function StudentAttendanceAbsence() {
  const courseId = useSelector((state) => state.auth.user.courseId);
  const [absenceList, setAbsenceList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [openModal, setOpenModal] = useState(false);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [reason, setReason] = useState('');
  const [file, setFile] = useState(null);
  const [currentItem, setCurrentItem] = useState(null);
  const fileInputRef = useRef(null);
  const [fileObject, setFileObject] = useState(null);

  useEffect(() => {
    const fetchAbsenceList = async (courseId) => {
      try {
        setLoading(true);
        const response = await absenceAttendancesApi.getAbsenceAttendanceListByStudent(courseId);

        if (response.data && response.data.data && response.data.data.content) {
          setAbsenceList(response.data.data.content);
        } else {
          setAbsenceList([]);
        }
        setLoading(false);
      } catch (err) {
        console.error('유고 결석 데이터 조회 실패:', err);
        setError('유고 결석 데이터를 불러오는데 실패했습니다.');
        setLoading(false);
      }
    };

    if (courseId) {
      fetchAbsenceList(courseId);
    }
  }, [courseId]);

  const handleFileChange = (e) => {
    // 이벤트에서 직접 파일 객체 가져오기
    const selectedFile = e.target.files[0];
    console.log("파일 선택됨:", selectedFile);
    setFileObject(selectedFile);
  };

  const handleTagChange = (item) => {
    setCurrentItem(item);
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setCurrentItem(null);
  };

  const handleEdit = (item) => {
    setCurrentItem(item);
    if (item.category === 'ABSENCE') setEditActiveIndex(0);
    else if (item.category === 'EARLY_LEAVE') setEditActiveIndex(1);
    else if (item.category === 'LATE') setEditActiveIndex(2);

    setStartDate(new Date(item.startDate));
    setEndDate(new Date(item.endDate));
    setReason(item.reason || '');

    setOpenModal(true);
  };

  const handleDelete = (item) => {
    // 삭제 로직 구현
    console.log('삭제:', item);
  };

  const handleInfoEdit = (item) => {
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

    // 수정된 항목 생성
    const updatedItem = {
      ...currentItem,
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      category: categoryMap[editActiveIndex],
      reason: reason,
    };

    // 리스트 업데이트 (map 함수 사용)
    setAbsenceList((prevList) =>
      prevList.map((item) =>
        item.absenceAttendanceId === currentItem.absenceAttendanceId ? updatedItem : item,
      ),
    );

    // 모달 닫기 및 상태 초기화
    setOpenModal(false);
    setCurrentItem(null);

    alert('수정이 완료되었습니다.');
  };

  const categoryMap = {
    0: 'ABSENCE',
    1: 'EARLY_LEAVE',
    2: 'LATE',
  };

  const handleSubmit = async () => {
    if (!startDate || !endDate) {
      alert('시작일과 종료일을 입력해주세요.');
      return;
    }

    if (!reason) {
      alert('사유를 입력해주세요.');
      return;
    }

    const formatDate = (date) => {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
      };
      const fileInput = fileInputRef.current;
      const file = fileInput?.files[0];
      console.log('선택된 파일:', file);
    try {
      const absenceData = {
        startDate: formatDate(startDate),
        endDate: formatDate(endDate),
        category: categoryMap[isActiveIndex],
        reason: reason,
        file: file,
      };

      const response = await absenceAttendancesApi.submitAbsenceAttendance(courseId, absenceData);

      if (response.data && response.data.data) {
        setAbsenceList((prevList) => [response.data.data, ...prevList]);

        if (fileInputRef.current) {
          fileInputRef.current.value = '';
        }

        setStartDate(null);
        setEndDate(null);
        setReason('');
        setFile(null);

        alert('유고 결석 신청이 완료되었습니다.');
      }
    } catch (error) {
      console.error('유고 결석 신청 실패:', error);

      if (error.response && error.response.data && error.response.data.message) {
        alert(`신청 실패: ${error.response.data.message}`);
      } else {
        alert('유고 결석 신청 중 오류가 발생했습니다.');
      }
    }
  };

  const list = ['결석', '조퇴', '지각'];

  const [isActiveIndex, setIsActiveIndex] = useState(0);
  const handleActiveFilter = (index) => {
    setIsActiveIndex(index);
  };

  const [editActiveIndex, setEditActiveIndex] = useState(0);
  const handleEditActiveFilter = (index) => {
    setEditActiveIndex(index);
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

  const editRoundButtons = list.map((item, index) => (
    <RoundButton
      key={index}
      index={index}
      isActiveIndex={editActiveIndex}
      title={item}
      handleActiveFilter={handleEditActiveFilter}
    />
  ));

  if (loading) {
    return <div className={styles.loading}>데이터를 불러오는 중입니다...</div>;
  }

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  const absenceListItems = absenceList.map((item, index) => {
    let statusText;
    if (item.isApprove === true || item.isApprove === 'T') {
      statusText = '승인';
    } else if (item.isApprove === false || item.isApprove === 'F') {
      statusText = '반려';
    } else {
      statusText = '대기';
    }

    const modifiedItem = {
      ...item,
      isApprove: statusText,
    };

    return (
      <LeftLineListItem
        key={index}
        isClickable={false}
        status={statusText}
        children={modifiedItem}
        onTagChange={() => handleTagChange(item)}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    );
  });
  const editInputBox = (
    <>
      <div className={styles.editInputBox}>
        {' '}
        <div className={styles.editCategoryButton}>{editRoundButtons}</div>
        <div className={styles.applicationDate}>
          <NewInputBox label="신청 날짜" value={'2025-04-03'}></NewInputBox>
        </div>
        <p className={styles.editTermTitle}>기간</p>
        <div className={styles.editDateInputBox}>
          <DatePicker
            selected={startDate}
            onChange={setStartDate}
            dateFormat="yyyy-MM-dd"
            className={styles.editDateInput}
            locale={ko}
            placeholderText="시작일"
            maxDate={endDate}
            utcOffset={0}
          />
          <span className={styles.editDateSeparator}>~</span>
          <DatePicker
            selected={endDate}
            onChange={setEndDate}
            dateFormat="yyyy-MM-dd"
            className={styles.editDateInput}
            locale={ko}
            placeholderText="종료일"
            minDate={startDate}
            utcOffset={0}
          />
        </div>
        <div className={styles.editTextInput}>
          <NewInputBox
            label="서류"
            title="파일 선택 또는 끌어놓기..."
            type="file"
            onChange={(e) => setFile(e.target.files[0])}
            innerRef={fileInputRef}
          />
          <div className={styles.editReason}>
            <NewInputBox label="사유" value={reason} onChange={(e) => setReason(e.target.value)} />
          </div>
        </div>
      </div>
    </>
  );

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
            <div className={styles.inputBox}>
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

      <div className={styles.editModalContainer}>
        <Modal
          isOpen={openModal}
          onClose={handleCloseModal}
          isEnable={true}
          mainClick={handleInfoEdit}
          mainText={'수정'}
          content={editInputBox}
        />
      </div>
    </>
  );
}
