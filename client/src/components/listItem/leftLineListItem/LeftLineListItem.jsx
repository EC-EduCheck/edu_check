import React from 'react';
import styles from './LeftLineListItem.module.css';
import { getBackgroundColor } from '../../../utils/buttonContentList';
import MoreButton from '../../buttons/moreButton/MoreButton';


export default function LeftLineListItem({
  isClickable,
  handleClick,
  status,
  children,
  onEdit,
  onDelete,
}) {
  const categoryMapper = {
    ABSENCE: '결석',
    LATE: '지각',
    EARLY_LEAVE: '조퇴',
  };

  const statusMapper = {
    true: '승인',
    false: '반려',
    null: '미승인',
  };

  const bgColor = getBackgroundColor(status);
  const fontColor = getBackgroundColor(status);

  return (
    <div
      {...(isClickable ? { onClick: handleClick } : {})}
      className={`${styles.leftLineListItem} ${isClickable && `${styles.active}`}`}
    >
      <div className={`${styles.colorLine} ${bgColor ? styles[bgColor] : ''}`}></div>
      {children?.studentName ? (
        // 관리자 - 유고결석
        <div className={styles.staffContainer}>
          <div className={styles.info}>
            <p>{children.studentName}</p>
            <p>{categoryMapper[children.category] || ''}</p>
            <p>{children.attached ? '첨부' : '미첨부'}</p>
            <p>{new Date(children.createdAt).toLocaleDateString().replace(/\.$/, '')}</p>
          </div>
          <p>{statusMapper[children.status]}</p>
        </div>
      ) : (
        // 수강생 - 유고결석
        <>
          <div className={styles.leftBox}>
            <p>{categoryMapper[children.category]}</p>
            <p className={`${fontColor ? styles[fontColor] : ''}`}>{children.isApprove}</p>
          </div>

          <div className={styles.rightBox}>
            <p>
              {children.startDate}~{children.endDate}
            </p>
            {!isClickable && (
              <MoreButton
                onEdit={() => onEdit(children)}
                onDelete={() => onDelete(children)}
                item={children}
              />
            )}
          </div>
        </>
      )}
    </div>
  );
}
