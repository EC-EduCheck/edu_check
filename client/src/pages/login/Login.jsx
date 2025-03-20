import React from 'react'
import styles from "./Login.module.css"
import InputBox from '../../components/inputBox/InputBox';
import MainButton from '../../components/buttons/mainButton/MainButton';

export default function Login() {
  const handleChange = () => {

  }
  
  return (
    <div className={styles.login}>
      <div className={styles.logoImage}>
        <img src="../../assets/logo.png" alt="user image" />
      </div>
      <InputBox type="email" title="이메일" disabled={false} onChange={handleChange}></InputBox>
      <InputBox
        type="password"
        title="비밀번호"
        disabled={false}
        onChange={handleChange}
      ></InputBox>
      <div className={styles.loginButton}>
        <MainButton title="로그인"></MainButton>
      </div>
    </div>
  );
}
