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
import { fi, ko } from 'date-fns/locale';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { URL_PATHS } from '../../constants/urlPaths';

export default function StudentAttendanceAbsence() {
  const courseId = useSelector((state) => state.auth.user.courseId);
  const [absenceList, setAbsenceList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [openModal, setOpenModal] = useState(false);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [reason, setReason] = useState('');
  const [currentItem, setCurrentItem] = useState(null);
  const fileInputRef = useRef(null);
  const [files, setFiles] = useState(null);
  const [fileNames, setFileNames] = useState('');
  const navigate = useNavigate();
  const [isActiveIndex, setIsActiveIndex] = useState(0);
  const categoryMap = {
    0: 'ABSENCE',
    1: 'EARLY_LEAVE',
    2: 'LATE',
  };
  console.log(isActiveIndex);
  const [uploadData, setUploadData] = useState({
    category: categoryMap[isActiveIndex],
    startDate: '',
    endDate: '',
    reason: '',
  });

  const resetFormFields = () => {
    setUploadData({
      category: categoryMap[isActiveIndex],
      startDate: '',
      endDate: '',
      reason: '',
    });
    setFiles(null);
    setFileNames('');

    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

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

  useEffect(() => {
    if (courseId) {
      fetchAbsenceList(courseId);
    }
  }, [courseId]);

  const handleFileChange = (e) => {
    const selectedFiles = Array.from(e.target.files);
    const names = selectedFiles.map((file) => file.name).join(',');

    setFiles(selectedFiles);
    setFileNames(names);
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

  const handleDelete = async (item) => {
    try {
      if (!window.confirm('정말로 이 유고 결석 신청내역을 삭제하시겠습니까?')) {
        return;
      }

      const response = await absenceAttendancesApi.deleteAbsenceAttendance(
        courseId,
        item.absenceAttendanceId,
      );

      setAbsenceList((prevList) =>
        prevList.filter((listItem) => listItem.absenceAttendanceId !== item.absenceAttendanceId),
      );

      alert('유고 결석 신청이 성공적으로 삭제되었습니다.');
    } catch (error) {
      console.error('유고 결석 삭제 실패:', error);

      if (error.response && error.response.data && error.response.data.message) {
        alert(`삭제 실패: ${error.response.data.message}`);
      } else {
        alert('유고 결석 삭제 중 오류가 발생했습니다.');
      }
    }
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

    const updatedItem = {
      ...currentItem,
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      category: categoryMap[editActiveIndex],
      reason: reason,
    };

    setAbsenceList((prevList) =>
      prevList.map((item) =>
        item.absenceAttendanceId === currentItem.absenceAttendanceId ? updatedItem : item,
      ),
    );

    setOpenModal(false);
    setCurrentItem(null);

    alert('수정이 완료되었습니다.');
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

  const handleOnChange = (name, value) => {
    setUploadData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData();

    const formatDate = (date) => {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    };

    const jsonData = {
      startDate: formatDate(uploadData.startDate),
      endDate: formatDate(uploadData.endDate),
      category: uploadData.category,
      reason: uploadData.reason,
    };

    formData.append('data', new Blob([JSON.stringify(jsonData)], { type: 'application/json' }));

    files.forEach((file) => {
      formData.append('files', file);
    });

    try {
      const response = await absenceAttendancesApi.submitAbsenceAttendance(courseId, formData);
      if (response.status === 200 || response.status === 201) {
        alert('유고 결석 신청이 완료되었습니다.');
        resetFormFields();
        if (courseId) {
          fetchAbsenceList(courseId);
        }
      }
    } catch (error) {
      console.error(error);
    }
  };

  const list = ['결석', '조퇴', '지각'];

  const handleActiveFilter = (index) => {
    setIsActiveIndex(index);

    setUploadData((prev) => ({
      ...prev,
      category: categoryMap[index],
    }));
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
        onDelete={() => handleDelete(item)}
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
            <NewInputBox label="사유" value={reason} onChange={handleOnChange} />
          </div>
        </div>
      </div>
    </>
  );

  return (
    <>
      <div className={styles.LeftLineListItemDisplay}>
        <div className={styles.absenceLeftLineListItem}>
          <p className="subTitle">신청 내역(페이지네이션 필요)</p>
          <div className={styles.absenceAttendanceList}>{absenceListItems}</div>
        </div>

        <div className={styles.absenceDashBoardItem}>
          <p className="subTitle">유고 결석 신청</p>
          <DashBoardItem>
            <div className={styles.inputBox}>
              <div className={styles.categoryButton}>{roundButtons}</div>
              <p className={styles.termTitle}>기간</p>
              <div className={styles.dateInputBox}>
                <DatePicker
                  selected={uploadData.startDate}
                  onChange={(data) => handleOnChange('startDate', data)}
                  dateFormat="yyyy-MM-dd"
                  className={styles.dateInput}
                  locale={ko}
                  placeholderText="시작일"
                  maxDate={endDate}
                  utcOffset={0}
                />
                <span className={styles.dateSeparator}>~</span>
                <DatePicker
                  selected={uploadData.endDate}
                  onChange={(data) => handleOnChange('endDate', data)}
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
                  isMultiple={true}
                  onChange={handleFileChange}
                  innerRef={fileInputRef}
                />
                <div className={styles.reason}>
                  <NewInputBox
                    label="사유"
                    name="reason"
                    type="text"
                    value={uploadData.reason}
                    onChange={(event) => handleOnChange('reason', event.target.value)}
                  />
                </div>
              </div>

              <div className={styles.submitButton}>
                <MainButton isEnable={true} title="신청" handleClick={handleSubmit} />
              </div>
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
