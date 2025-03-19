import React, { useEffect, useState } from 'react';
import styles from './RoundButton.module.css';

export default function RoundButton({ index, isActiveIndex, title, handleActiveFilter }) {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    if (index === isActiveIndex) {
      setIsActive(true);
    } else {
      setIsActive(false);
    }
  }, [isActiveIndex]);

  // TODO : 상위 부모에 주석을 붙여넣으면 현재 index 인식하고 배경이 바뀝니다
  // const list = ['결석', '조퇴', '지각'];

  // const [isActiveIndex, setIsActiveIndex] = useState(0);
  // const handleActiveFilter = (index) => {
  //   setIsActiveIndex(index);
  // };

  // const roundButtons = list.map((item, index) => {
  //   return (
  //     <RoundButton
  //       key={index}
  //       index={index}
  //       isActiveIndex={isActiveIndex}
  //       title={item}
  //       handleActiveFilter={handleActiveFilter}
  //     ></RoundButton>
  //   );
  // });

  // TODO : 상위 버튼들을 감싸는 부모 박스는 아래와 같이 스타일을 주면 부모 크기에 맞게 조절이 됩니다
  // <div style={{ display: 'flex', width: '100%' }}>{roundButtons}</div>

  return (
    <button
      className={`${styles.roundButton} ${isActive && `${styles.active}`}`}
      onClick={() => handleActiveFilter(index)}
    >
      {title}
    </button>
  );
}
