import React from 'react'
import styles from "./Modal.module.css"
import MainButton from '../buttons/mainButton/MainButton';

export default function Modal({ isOpen, onClose, content, confirmText, cancelText, onConfirm }) {
  if (!isOpen) return null;
  return (
    <>
      <div className={styles.backGround}></div>
      <div className={styles.container}>
        <div>
          <p>{content}</p>

          {/* todo: MainButton 확인 후 적용 */}
          <div className={styles.buttonBox}>
            {onConfirm && <MainButton onClick={onConfirm}>{confirmText || '확인'}</MainButton>}
            <MainButton onClick={onClose}>{cancelText || '취소'}</MainButton>
          </div>
        </div>
      </div>
    </>
  );
}
