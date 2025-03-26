import React, { useEffect, useState } from 'react';
import styles from './StaffAttendanceAbsence.module.css';
import { absenceAttendancesApi } from '../../api/absenceAttendancesApi';
import LeftLineListItem from '../../components/listItem/leftLineListItem/LeftLineListItem';
import { useSelector } from 'react-redux';
import MainButton from '../../components/buttons/mainButton/MainButton';
import { activeTitle } from '../../utils/buttonContentList';
import Modal from '../../components/modal/Modal';

export default function StaffAttendanceAbsence() {
  const [data, setData] = useState();
  const { role, courseId } = useSelector((state) => state.auth.user);
  const buttonList = ['전체', '승인', '반려', '미승인'];
  const [selected, setSelected] = useState('전체');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState();

  const openModal = (item) => {
    setModalContent(item);
    setIsModalOpen(true);
  };

  const initActiveTitle = () => {
    for (let i = activeTitle.length - 1; i >= 0; i--) {
      if (buttonList.includes(activeTitle[i])) {
        activeTitle.splice(i, 1);
      }
    }
  };

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await absenceAttendancesApi.getAbsenceAttendancesByCourseId(courseId);
        setData(response.data.data);
      } catch (error) {
        console.error(error);
      }
    }

    initActiveTitle();
    setSelected('전체');
    activeTitle.push('전체');
    fetchData();
    return () => {
      initActiveTitle();
      setSelected('전체');
      activeTitle.push('전체');
    };
  }, [courseId]);

  const handleButtonClick = (title) => {
    initActiveTitle();
    activeTitle.push(title);
    setSelected(title);
  };

  return (
    <>
      <div className="buttonContainer">
        {buttonList.map((title, idx) => (
          <MainButton
            key={idx}
            title={title}
            isActive={activeTitle.includes(title)}
            isEnable={true}
            handleClick={() => handleButtonClick(title)}
          />
        ))}
      </div>

      {data?.absenceAttendances &&
        data.absenceAttendances
          .filter((item) => {
            if (selected === '전체') return true;
            if (selected === '승인') return item.status === true;
            if (selected === '반려') return item.status === false;
            return item.status === null;
          })
          .map((item, idx) => (
            <LeftLineListItem
              isClickable={role === 'MIDDLE_ADMIN'}
              status={item.status}
              children={item}
              handleClick={() => openModal(item)}
              key={idx}
            />
          ))}

      {isModalOpen && (
        <Modal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          mainClick={() => console.log('반려 처리')}
          subClick={() => console.log('승인 처리')}
          mainText="반려"
          subText="승인"
          content={'content'}
          // content={modalContent}
        />
      )}
    </>
  );
}
