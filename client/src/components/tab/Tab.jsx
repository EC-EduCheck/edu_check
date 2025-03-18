import React from 'react';
import styles from './Tab.module.css';
import TabButton from './tabButton/TabButton';
import { getTabContent } from '../../utils/tabContentUtils';

export default function Tab({ menuType }) {
  const tabContent = getTabContent(menuType).map((item, index) => {
    return <TabButton key={index} index={index} item={item}></TabButton>;
  });

  return <div className={styles.tab}>{tabContent}</div>;
}
