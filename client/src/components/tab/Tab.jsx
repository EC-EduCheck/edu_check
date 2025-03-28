import React from 'react';
import { useSelector } from 'react-redux';
import styles from './Tab.module.css';
import TabButton from './tabButton/TabButton';
import { tabList } from '../../constants/tab';
import { useLocation } from 'react-router-dom';
import { getNthSegment } from '../../utils/routerUtils';

export default function Tab() {
  const location = useLocation();

  const { role } = useSelector((state) => state.auth.user);
  const segment = getNthSegment(location.pathname, 2);
  const renderTabList = tabList?.[role]?.[segment];

  const tabContent = renderTabList?.map((item, index) => {
    return <TabButton key={`tab-${index}`} index={index} item={item}></TabButton>;
  });

  return <div className={styles.tab}>{tabContent}</div>;
}
