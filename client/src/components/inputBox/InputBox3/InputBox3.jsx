import React from 'react';
import styles from './InputBox3.module.css';

export default function InputBox3({
  type,
  title,
  disabled,
  onChange,
  label,
  isSelect,
  content,
  name,
}) {

  return (
    <div className={styles.inputContainer3}>
      <p className={styles.label3}>
        {label}
        <span>{isSelect}</span>
      </p>
      <input
        className={styles.inputBox3}
        type={type}
        placeholder={title}
        disabled={disabled}
        onChange={onChange}
        name={name}
      ></input>
      <p className={styles.content3}>{content}</p>
    </div>
  );
}
