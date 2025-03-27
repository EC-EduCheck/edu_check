import React, { useEffect, useState } from 'react';
import styles from './StudentSetting.module.css';
import InputBox from '../../components/inputBox/InputBox';
import MainButton from '../../components/buttons/mainButton/MainButton';

export default function StudentSetting() {
  const [error, setError] = useState('');
  const [input, setInput] = useState({
    phoneNumber: '',
    password: '',
  });

  const validatePhoneNumber = (phoneNumber) => {
    const phoneRegex = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
    return phoneRegex.test(phoneNumber);
  };

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
    return passwordRegex.test(password);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  useEffect(() => {
    if (!validatePhoneNumber(input.phoneNumber)) {
      setError('연락처를 다시 입력해주세요.');
    } else if (!validatePassword(input.password)) {
      setError('비밀번호는 8자 이상, 대소문자, 숫자, 특수문자를 포함해야 합니다.');
    } else {
      setError('');
      alert('개인정보가 수정 되었습니다.');
    }
  }, [input.phoneNumber, input.password]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInput((preInput) => ({
      ...preInput,
      [name]: value,
    }));
  };

  return (
    <form className={styles.studentSetting} onSubmit={handleSubmit}>
      <InputBox
        type="text"
        title="연락처"
        disabled={false}
        onChange={handleChange}
        label="연락처"
        isSelect="(선택)"
      ></InputBox>

      <div className={styles.smallInputBox}>
        <InputBox
          type="text"
          title="년도"
          disabled={false}
          onChange={handleChange}
          label="생년월일"
          isSelect="(선택)"
        ></InputBox>

        <InputBox type="text" title="월" disabled={false} onChange={handleChange}></InputBox>

        <InputBox type="text" title="일" disabled={false} onChange={handleChange}></InputBox>
      </div>

      <InputBox
        type="password"
        title="비밀번호"
        disabled={false}
        onChange={handleChange}
        label="비밀번호 변경"
        isSelect="(선택)"
        content="비밀번호는 8자리이상입니다."
      ></InputBox>

      <div className={styles.noneInputBox}>
        <InputBox
          type="password"
          title="비밀번호 확인"
          disabled={false}
          onChange={handleChange}
          isSelect={true}
          content="비밀번호가 일치하지 않습니다."
        ></InputBox>
      </div>

      <InputBox
        type="password"
        title="현재 비밀번호"
        disabled={false}
        onChange={handleChange}
        label="현재 비밀번호"
        isSelect="(필수)"
        content="현재 비밀번호는 필수 입력값입니다."
      ></InputBox>

      {error && <div style={{ color: 'red' }}></div>}

      <MainButton title="수정"></MainButton>
    </form>
  );
}
