import React, { useEffect, useState, useRef } from 'react';
import styles from './SideBar.module.css';
import { sideBarList } from '../../utils/sideBarList';
import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';
import { useGeolocated } from 'react-geolocated';
import { attendanceApi } from '../../api/attendanceApi';

export default function SideBar() {
  const [isOpen, setIsOpen] = useState(false);
  const infoRef = useRef(null);
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

  const submitAttendanceAPI = async (latitude, longitude) => {
    try {
      const data = await attendanceApi.submitAttendance(latitude, longitude);
      alert(data.message);
      console.log(data.data.status);
    } catch (error) {
      alert('출석 체크에 실패했습니다.');
    }
  };

  const handleAttendanceCheck = () => {
    if (!isGeolocationAvailable) {
      alert('브라우저가 위치 정보를 지원하지 않습니다.');
      return;
    }

    if (!isGeolocationEnabled) {
      navigator.permissions.query({ name: 'geolocation' }).then(function (result) {
        if (result.state === 'prompt') {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              console.log('위도:', position.coords.latitude, '경도:', position.coords.longitude);
              submitAttendanceAPI(position.coords.latitude, position.coords.longitude);
            },
            (error) => {
              console.error('위치 정보 오류:', error);
              alert('위치 정보를 가져오는데 실패했습니다.');
            },
            {
              enableHighAccuracy: true,
              timeout: 10000,
              maximumAge: 0,
            },
          );
        } else if (result.state === 'denied') {
          alert('위치 정보 접근이 차단되었습니다. 브라우저 설정에서 권한을 허용해주세요.');
        }
      });
      return;
    }

    getPosition();
  };

  useEffect(() => {
    if (coords) {
      console.log('위도:', coords.latitude, '경도:', coords.longitude);
      submitAttendanceAPI(coords.latitude, coords.longitude);
    }
  }, [coords]);

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

  const sideBarItems = sideBarList.map((item, index) => {
    return <SideBarItem key={index} index={index} item={item}></SideBarItem>;
  });

  return (
    <div className={styles.sideBar}>
      <div ref={infoRef} onClick={() => setIsOpen(true)} className={styles.memberInfo}>
        <div className={styles.memberInfoImg}>
          <img src="./assets/logo.png" alt="user image" />
        </div>

        {/* todo : member 이름, 과정 가져오기 */}
        <div className={styles.memberInfoDetail}>
          <h1>수강생</h1>
          <p>클라우드 기반 JAVA 풀스택 웹개발</p>
        </div>

        {/* todo : member 개인정보 변수 필요 */}
        <div className={`${styles.memberInfoMore} ${isOpen && `${styles.isOpen}`}`}>
          <ul>
            <li>
              <p>생년월일</p>
              <p>2025.01.01</p>
            </li>
            <li>
              <p>연락처</p>
              <p>010-1234-5678</p>
            </li>
            <li>
              <p>이메일</p>
              <p>hoho@example.com</p>
            </li>
          </ul>
          {/* todo: 로그아웃 기능 추가 */}
          <MainButton title="로그아웃"></MainButton>
        </div>
      </div>

      <div>
        {error && <div>위치 정보를 가져오는 데 실패했습니다: {error.message}</div>}
        <MainButton title="출석하기" handleClick={handleAttendanceCheck}></MainButton>
      </div>
      <nav>{sideBarItems}</nav>
    </div>
  );
}
