import React from 'react';
import styles from './TabButton.module.css';
import { useDispatch, useSelector } from 'react-redux';
import { updateTab } from '../../../store/slices/sideBarItemSlice';

export default function TabButton({ index, item }) {
  const dispatch = useDispatch();
  const currentTabItem = useSelector((state) => state.sideBarItem.tab);
  const isActive = currentTabItem === index;

  const handleClick = () => {
    dispatch(updateTab(index));
  };

  return (
    <button
      className={`${styles.tabButton} ${isActive ? `${styles.active}` : ''}`}
      onClick={handleClick}
    >
      {item}
    </button>
  );
}