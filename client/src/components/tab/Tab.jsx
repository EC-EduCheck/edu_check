import React from 'react';
import { useSelector } from 'react-redux';
import styles from './Tab.module.css';
import TabButton from './tabButton/TabButton';

export default function Tab() {
  const { tabContentsList } = useSelector((state) => state.sideBarItem);

  const tabContent = tabContentsList.map((item, index) => {
    return <TabButton key={index} index={index} item={item}></TabButton>;
  });

  return <div className={styles.tab}>{tabContent}</div>;
}
