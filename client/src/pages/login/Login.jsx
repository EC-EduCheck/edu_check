import React, { useState } from 'react';
import styles from './Login.module.css';
import InputBox from '../../components/inputBox/InputBox';
import MainButton from '../../components/buttons/mainButton/MainButton';
import { authApi } from '../../api/authApi';

export default function Login() {
  const [inputData, setInputData] = useState({
    email: '',
    password: '',
  });

  const handleInputChange = (event) => {
    setInputData((prev) => ({
      ...prev,
      [event.target.name]: event.target.value,
    }));
  };

  const handleLoginButtonClick = async (event) => {
    event.preventDefault();
    try {
      const response = await authApi.login(inputData.email, inputData.password);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={styles.login}>
      <div className={styles.logoImage}>
        <img src="./assets/logo.png" alt="user image" />
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
        <MainButton handleClick={handleLoginButtonClick} title="로그인"></MainButton>
      </div>
    </div>
  );
}
