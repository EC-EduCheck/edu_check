import React from 'react';
import styles from './TabButton.module.css';
import { useDispatch, useSelector } from 'react-redux';
import { updateTab } from '../../../store/slices/sideBarItemSlice';

export default function TabButton({ index, item }) {
  const dispatch = useDispatch();

  const currentTabItem = useSelector((state) => state.sideBarItem.tab);
  const currentStyle = currentTabItem === index ? { borderBottom: '2px solid #333' } : {};

  const handleClick = () => {
    dispatch(updateTab(index));
  };

  return (
    <button style={currentStyle} className={styles.tabButton} onClick={handleClick}>
      {item}
    </button>
  );
}
