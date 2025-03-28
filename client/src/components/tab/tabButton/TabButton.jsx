import React from 'react';
import styles from './TabButton.module.css';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

export default function TabButton({ index, item }) {
  const navigate = useNavigate();
  const currentTabItem = useSelector((state) => state.sideBarItem.tab);
  const isActive = currentTabItem === index;

  return (
    <button
      className={`${styles.tabButton} ${isActive ? `${styles.active}` : ''}`}
      onClick={() => navigate(item.path)}
    >
      {item.name}
    </button>
  );
}
