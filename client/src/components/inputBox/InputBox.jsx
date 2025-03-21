import React from 'react'
import styles from "./InputBox.module.css"

export default function InputBox({
  type,
  title,
  disabled,
  onChange,
  label,
  isSelect,
  content,
  name,
}) {
  const selectText = isSelect ? '(선택)' : '(필수)';

  return (
    <div className={styles.inputContainer}>
      <p className={styles.label}>
        {label} <span>{selectText}</span>
      </p>
      <input
        className={styles.inputBox}
        type={type}
        placeholder={title}
        disabled={disabled}
        onChange={onChange}
        name={name}
      ></input>
      <p className={styles.content}>{content}</p>
    </div>
  );
}
