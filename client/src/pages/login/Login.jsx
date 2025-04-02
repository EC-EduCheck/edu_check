import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import styles from './Login.module.css';
import { authApi } from '../../api/authApi';
import { login } from '../../store/slices/authSlice';

import InputBox from '../../components/inputBox/InputBox';
import MainButton from '../../components/buttons/mainButton/MainButton';
import { BASE_PATHS, URL_PATHS } from '../../constants/urlPaths';

export default function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  const lastLoginDate = useSelector((state) => state.auth.user.lastLoginDate);
  const lastPasswordChangeDate = useSelector((state) => state.auth.user.lastPasswordChangeDate);
  const { role } = useSelector((state) => state.auth.user);
  const [inputData, setInputData] = useState({
    email: '',
    password: '',
  });
  const [isLoginButtonEnable, setIsLoginButtonEnable] = useState(false);

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setInputData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleLoginButtonClick = async (event) => {
    event.preventDefault();
    setIsLoginButtonEnable(false);
    try {
      const response = await authApi.login(inputData.email, inputData.password);
      const accessToken = response.headers?.authorization ?? '';
      dispatch(
        login({
          ...response.data.data,
          accessToken: accessToken,
        }),
      );
    } catch (error) {
      // TODO: BE에서 에러처리 후 응답 메시지 출력으로 변경
      console.log(error);
      alert('로그인에 실패했습니다. 아이디 혹은 비밀번호를 확인하세요');
      setIsLoginButtonEnable(true);
    }
  };

  useEffect(() => {
    if (isLoggedIn) {
      if (role === 'STUDENT' && !lastPasswordChangeDate) {
        const settingPath = URL_PATHS?.[role].SETTING;
        alert('초기 비밀번호를 변경하세요,');
        navigate(settingPath, { replace: true });
      } else {
        const mainPath = BASE_PATHS?.[role];
        navigate(mainPath, { replace: true });
      }
    }
  }, [isLoggedIn, navigate, lastPasswordChangeDate]);

  useEffect(() => {
    setIsLoginButtonEnable(
      inputData.email.trim().length > 0 && inputData.password.trim().length > 0,
    );
  }, [inputData]);

  return (
    <form onSubmit={(e)=> e.preventDefault()} >
      <div className={styles.login}>
        <div className={styles.logoImage}>
          <img src="/assets/logo.png" alt="user image" />
        </div>
        <InputBox
          type="email"
          name="email"
          disabled={false}
          onChange={handleInputChange}
          title="이메일을 입력하세요."
        />
        <InputBox
          type="password"
          name="password"
          disabled={false}
          onChange={handleInputChange}
          title="비밀번호를 입력하세요."
        />
        <div className={styles.loginButton}>
          <MainButton
            handleClick={handleLoginButtonClick}
            title="로그인"
            isEnable={isLoginButtonEnable}
          ></MainButton>
        </div>
      </div>
    </form>
  );
}
