import React, { useEffect, useState, useRef } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { checkIn, completeAttendance, logout } from '../../store/slices/authSlice';

import styles from './SideBar.module.css';

import SideBarItem from './sidebarItem/SidebarItem';
import MainButton from '../buttons/mainButton/MainButton';
import { useGeolocated } from 'react-geolocated';
import { attendanceApi } from '../../api/attendanceApi';
import { authApi } from '../../api/authApi';
import { useNavigate } from 'react-router-dom';
import { sidebarList } from '../../constants/sidebar';

export default function SideBar() {
  const infoRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { name, role, courseName, phoneNumber, birthDate, email } = useSelector(
    (state) => state.auth.user,
  );
  const { isLoggedIn } = useSelector((state) => state.auth);
  const { isCheckedIn, attendanceDate, isCompleted } = useSelector(
    (state) => state.auth.attendanceStatus,
  );

  const today = new Date().toISOString().split('T')[0];
  const isAttendanceToday = attendanceDate === today;

  const { coords, isGeolocationAvailable, error, getPosition } = useGeolocated({
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
    if (isCheckedIn && isAttendanceToday && !isCompleted) {
      handleCompleteAttendance();
    } else if (!isCheckedIn || !isAttendanceToday) {
      handleCheckIn();
    }
  };

  const handleCheckIn = () => {
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
    if (isLoggedIn) {
      const storedDate = attendanceDate;
      const currentDate = new Date().toISOString().split('T')[0];

      if (storedDate && storedDate !== currentDate) {
        dispatch(resetAttendanceStatus());
      }
    }
  }, [isLoggedIn, attendanceDate, dispatch]);
  const handleCompleteAttendance = async () => {
    try {
      const data = await attendanceApi.submitCheckOut();
      alert(data.message || '퇴실 처리되었습니다.');
      dispatch(completeAttendance());
    } catch (error) {
      console.error('퇴실 처리 오류:', error);
      alert('퇴실 처리에 실패했습니다: ' + (error.response?.data?.message || error.message));
    }
  };

  useEffect(() => {
    if (coords) {
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
      dispatch(checkIn());
    } catch (error) {
      console.error('출석 체크 오류:', error);
      alert('출석 체크에 실패했습니다: ' + (error.response?.data?.message || error.message));
    }
  };

  const getButtonProps = () => {
    if (isCompleted && isAttendanceToday) {
      return { title: '퇴실 완료', isEnable: false };
    } else if (isCheckedIn && isAttendanceToday) {
      return { title: '퇴실하기', isEnable: true };
    } else {
      return { title: '출석하기', isEnable: true };
    }
  };

  const buttonProps = getButtonProps();

  const renderSidebarList = sidebarList[role];

  const compareUrl = (pathName, itemPath) => {
    const getSegment = (url) => url.split('/').filter(Boolean).slice(0, 3);

    const segment1 = getSegment(pathName);
    const segment2 = getSegment(itemPath);

    return segment1.join('/') === segment2.join('/');
  };

  const sideBarItems = renderSidebarList?.map((item, index) => {
    return (
      <SideBarItem
        key={`sidebar-${index}`}
        item={item}
        isActive={compareUrl(location.pathname, item.path)}
      ></SideBarItem>
    );
  });

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

  const handleLogout = async () => {
    await authApi.logout();
    dispatch(logout());
    navigate('/');
  };

  return (
    <div className={styles.sideBar}>
      <div ref={infoRef} onClick={() => setIsOpen(true)} className={styles.memberInfo}>
        <div className={styles.memberInfoImg}>
          <img src="/assets/logo.png" alt="user image" />
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
          <MainButton title="로그아웃" handleClick={handleLogout} isEnable={true}></MainButton>
        </div>
      </div>

      <div>
        {error && <div>위치 정보를 가져오는 데 실패했습니다: {error.message}</div>}
        {role === 'STUDENT' && (
          <MainButton
            title={buttonProps.title}
            handleClick={handleAttendanceCheck}
            isEnable={buttonProps.isEnable}
          />
        )}
      </div>
      <nav>{sideBarItems}</nav>
    </div>
  );
}
