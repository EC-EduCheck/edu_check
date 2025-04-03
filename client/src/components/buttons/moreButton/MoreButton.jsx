import React, { useEffect, useRef, useState } from 'react'
import styles from "./MoreButton.module.css"
import DropBoxButton from "../../buttons/dropBoxButton/DropBoxButton"

export default function MoreButton({ onEdit, onDelete, item }) {
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    } else {
      document.removeEventListener('mousedown', handleClickOutside);
    }

    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [isOpen]);

  const handleEdit = () => {
    if (onEdit) {
      onEdit(item);
      setIsOpen(false);
    }
  };

  const handleDelete = () => {
    if (onDelete) {
      onDelete(item);
      setIsOpen(false);
    }
  };

  return (
    <div className={styles.moreButtonbox} ref={dropdownRef}>
      <button onClick={() => setIsOpen((prev) => !prev)} className={styles.moreButton}>
        <img src="/assets/more-icon.png" alt="더보기 아이콘" />
      </button>

      {isOpen && (
        <div className={styles.dropBox}>
          <DropBoxButton title="삭제" handleClick={handleDelete}></DropBoxButton>
          <DropBoxButton title="수정" handleClick={handleEdit}></DropBoxButton>
        </div>
      )}
    </div>
  );
}
