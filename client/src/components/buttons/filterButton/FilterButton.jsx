import React, { useEffect, useState } from 'react';
import styles from './FilterButton.module.css';

export default function FilterButton({ index, isActiveIndex, title, content, handleActiveFilter }) {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    if (index === isActiveIndex) {
      setIsActive(true);
    } else {
      setIsActive(false);
    }
  }, [isActiveIndex]);

  // TODO : 상위 부모에 주석을 붙여넣으면 현재 index 인식하고 배경이 바뀝니다
  // (현재 선택된 상태에서 한번 더 클릭할 경우 필터링이 풀립니다)
  // const list = ['출석', '조퇴', '지각', '결석];

  // const [isActiveIndex, setIsActiveIndex] = useState(false);
  // const handleActiveFilter = (index) => {
  //   if (index === isActiveIndex) {
  //     setIsActiveIndex(false);
  //   } else {
  //     setIsActiveIndex(index);
  //   }
  // };

  // const filterButtons = list.map((item, index) => {
  //   return (
  //     <FilterButton
  //       key={index}
  //       index={index}
  //       isActiveIndex={isActiveIndex}
  //       title={item}
  //       content="18명"
  //       handleActiveFilter={handleActiveFilter}
  //     ></FilterButton>
  //   );
  // });

  // TODO : 상위 버튼들을 감싸는 부모 박스는 아래와 같이 스타일을 주면 부모 크기에 맞게 조절이 됩니다
  // <div style={{ display: 'flex', width: '100%', gap: '1rem' }}>{filterButtons}</div>

  return (
    <button
      className={`${styles.filterButton} ${isActive && `${styles.active}`}`}
      onClick={() => handleActiveFilter(index)}
    >
      <p>{title}</p>
      <p>{content}</p>
    </button>
  );
}
