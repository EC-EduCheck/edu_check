import React from 'react';
import styles from './TabButton.module.css';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

export default function TabButton({ index, item, isActive }) {
  const navigate = useNavigate();

  return (
    <button
      className={`${styles.tabButton} ${isActive ? `${styles.active}` : ''}`}
      onClick={() => navigate(item.path)}
    >
      {item.name}
    </button>
  );
}
