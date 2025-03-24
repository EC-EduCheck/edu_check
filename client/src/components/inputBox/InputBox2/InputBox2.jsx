import React from 'react';
import styles from './InputBox2.module.css';

export default function InputBox2({
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
    <div className={styles.inputContainer2}>
      <p className={styles.label2}>
        {label}
        <span>{isSelect}</span>
      </p>
      <input
        className={styles.inputBox2}
        type={type}
        placeholder={title}
        disabled={disabled}
        onChange={onChange}
        name={name}
      ></input>
      <p className={styles.content2}>{content}</p>
    </div>
  );
}
