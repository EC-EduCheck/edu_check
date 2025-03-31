import React from 'react';
import { useSelector } from 'react-redux';
import styles from './Tab.module.css';
import TabButton from './tabButton/TabButton';
import { tabList } from '../../constants/tab';
import { useLocation } from 'react-router-dom';
import { getNthSegment } from '../../utils/routerUtils';
import { URL_PATHS } from '../../constants/urlPaths';

export default function Tab() {
  const location = useLocation();

  const { role } = useSelector((state) => state.auth.user);
  const segment = getNthSegment(location.pathname, 2);
  const renderTabList = tabList?.[role]?.[segment];

  const compareUrl = (pathName) => {
    const getSegment = (url) => url.split('/').filter(Boolean).slice(0, 4);
    const basicSegment = URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.DETAIL_PATTERN;

    const segment1 = getSegment(pathName);
    const segment2 = getSegment(basicSegment);

    return segment1.join('/') === segment2.join('/');
  };

  const tabContent = renderTabList?.map((item, index) => {
    let isActive = location.pathname === item.path;
    if (item.name === '세부 출결 현황') {
      isActive = compareUrl(location.pathname);
    }

    return (
      <TabButton key={`tab-${index}`} index={index} item={item} isActive={isActive}></TabButton>
    );
  });

  return <div className={styles.tab}>{tabContent}</div>;
}
