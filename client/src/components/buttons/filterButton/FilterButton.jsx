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

  return (
    <button
      className={`${styles.filterButton} ${isActive && `${styles.active}`}`}
      onClick={() => handleActiveFilter(index)}
    >
      <p>{title}</p>
      <p>{content}명</p>
    </button>
  );
}
