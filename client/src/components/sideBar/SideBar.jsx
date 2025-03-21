import React, { useEffect, useState, useRef } from 'react';
import { useSelector } from 'react-redux';

import styles from './SideBar.module.css';
import { studentSideBarList, staffSideBarList } from '../../utils/sideBarList';
import { roleList } from '../../utils/dashBoardList';

import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';
import { useGeolocated } from 'react-geolocated';
import { attendanceApi } from '../../api/attendanceApi';

export default function SideBar() {
  const infoRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const [itemList, setItemList] = useState([]);
  const { name, role, courseName, phoneNumber, birthDate, email } = useSelector(
    (state) => state.auth.user,
  );
  const { coords, isGeolocationAvailable, isGeolocationEnabled, error, getPosition } =
    useGeolocated({
      positionOptions: {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: 10000,
      },
      watchPosition: false,
      userDecisionTimeout: 5000,
      suppressLocationOnMount: true,
      isOptimisticGeolocationEnabled: false,
    });

    
  const handleAttendanceCheck = () => {
    if (!isGeolocationAvailable) {
      alert('브라우저가 위치 정보를 지원하지 않습니다.');
      return;
    }

    navigator.permissions
      .query({ name: 'geolocation' })
      .then(function (result) {
        if (result.state === 'granted' || result.state === 'prompt') {
          getPosition();
        } else if (result.state === 'denied') {
          alert('위치 정보 접근이 차단되었습니다. 브라우저 설정에서 권한을 허용해주세요.');
        }
      })
      .catch((error) => {
        console.error('권한 확인 오류:', error);
        getPosition();
      });
  };

  useEffect(() => {
    if (coords) {
      console.log('위도:', coords.latitude, '경도:', coords.longitude);
      submitAttendanceAPI(coords.latitude, coords.longitude);
    } else if (error) {
      console.error('위치 정보 오류:', error);
      alert('위치 정보를 가져오는데 실패했습니다: ' + error.message);
    }
  }, [coords, error]);

  const submitAttendanceAPI = async (latitude, longitude) => {
    try {
      const data = await attendanceApi.submitAttendance(latitude, longitude);
      alert(data.message);
      console.log(data);
    } catch (error) {
      console.error('출석 체크 오류:', error);
      alert('출석 체크에 실패했습니다: ' + (error.response?.data?.message || error.message));
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (infoRef.current && !infoRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  useEffect(() => {
    if (role === roleList[0]) {
      setItemList(studentSideBarList);
    } else {
      setItemList(staffSideBarList);
    }
  }, [role]);

  const sideBarItems = itemList.map((item, index) => {
    return <SideBarItem key={index} index={index} item={item}></SideBarItem>;
  });

  return (
    <div className={styles.sideBar}>
      <div ref={infoRef} onClick={() => setIsOpen(true)} className={styles.memberInfo}>
        <div className={styles.memberInfoImg}>
          <img src="../../assets/logo.png" alt="user image" />
        </div>

        <div className={styles.memberInfoDetail}>
          <h1>{name}</h1>
          <p>{courseName}</p>
        </div>

        <div className={`${styles.memberInfoMore} ${isOpen && `${styles.isOpen}`}`}>
          <ul>
            <li>
              <p>생년월일</p>
              <p>{birthDate}</p>
            </li>
            <li>
              <p>연락처</p>
              <p>{phoneNumber}</p>
            </li>
            <li>
              <p>이메일</p>
              <p>{email}</p>
            </li>
          </ul>
          {/* todo: 로그아웃 기능 추가 */}
          <MainButton title="로그아웃"></MainButton>
        </div>
      </div>

      <div>
        {error && <div>위치 정보를 가져오는 데 실패했습니다: {error.message}</div>}
        <MainButton
          title="출석하기"
          handleClick={handleAttendanceCheck}
          isEnable={true}
        ></MainButton>
      </div>
      <nav>{sideBarItems}</nav>
    </div>
  );
}
