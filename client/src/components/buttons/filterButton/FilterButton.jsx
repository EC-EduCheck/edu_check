import React, { useEffect, useState } from 'react';
import styles from './FilterButton.module.css';

export default function FilterButton({
  index,
  isActiveIndex,
  title,
  content,
  handleActiveFilter,
  isMultiSelect = false,
}) {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    if (isMultiSelect) {
      setIsActive(Array.isArray(isActiveIndex) && isActiveIndex.includes(index));
    } else {
      setIsActive(index === isActiveIndex);
    }
  }, [isActiveIndex, isMultiSelect]);

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
