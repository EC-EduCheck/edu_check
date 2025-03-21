import React from 'react';
import styles from './StudentSetting.module.css';
import InputBox from '../../components/inputBox/InputBox';
import MainButton from '../../components/buttons/mainButton/MainButton';

export default function StudentSetting() {
  const handleChange = () => {};

  return (
    <form className={styles.studentSetting}>
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

        <InputBox
          type="text"
          title="월"
          disabled={false}
          onChange={handleChange}
        ></InputBox>

        <InputBox
          type="text"
          title="일"
          disabled={false}
          onChange={handleChange}
        ></InputBox>
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

      <MainButton title="수정"></MainButton>
    </form>
  );
}
