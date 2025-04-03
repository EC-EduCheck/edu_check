import React from 'react';
import styles from './BaseListItem.module.css';
import Tag from '../../tag/Tag';

export default function BaseListItem({ content, phone, email, tagTitle, studentId, courseId, onTagChange, onClick }) {
  return (
    <div className={styles.baseListItem} onClick={onClick}>
      <div>
        <div>{content}</div>
        <p>{phone}</p>
        <p>{email}</p>
      </div>
      <Tag title={tagTitle} studentId={studentId} courseId={courseId} onTagChange={onTagChange} />
    </div>
  );
}
