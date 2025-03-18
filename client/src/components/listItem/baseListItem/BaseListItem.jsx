import React from 'react'
import styles from "./BaseListItem.module.css"
import Tag from '../../tag/Tag';

export default function BaseListItem({ content, tagTitle }) {
  return (
    <div className={styles.baseListItem}>
      <div>{content}</div>

      <Tag title={tagTitle}></Tag>
    </div>
  );
}
