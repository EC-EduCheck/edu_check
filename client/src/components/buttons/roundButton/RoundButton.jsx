import React, { useState } from 'react';
import styles from './RoundButton.module.css';

export default function RoundButton({ title, handleClick }) {
  // currentIndex 파악해 -> 현재 인덱스와 같은 값이면 -> bgColor 적용
  // 위의 기능을 이용하기 위해서는 부모에서 props로 해당하는 인덱스와 현재 선택된 currentIndex 필요
  // handleClick : 클릭 시 currentIndex가 현재 index로 변경되도록 함
  // const bgColor = currentIndex === index ? #b3e56a : '#ffffff';

  return (
    <button
      className={styles.roundButton}
      onClick={handleClick}
      // style={{ backgroundColor: bgColor }}
    >
      {title}
    </button>
  );
}
