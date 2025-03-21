import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styles from './Tab.module.css';
import TabButton from './tabButton/TabButton';
import { roleList } from '../../utils/dashBoardList';
import { getStudentTabContent, getStaffTabContent } from '../../utils/tabContentList';

export default function Tab({ menuType }) {
  const [tabContents, setTabContents] = useState([]);
  const { role } = useSelector((state) => state.auth.user);

  useEffect(() => {
    if (role === roleList[0]) {
      const result = getStudentTabContent(menuType);
      setTabContents(result);
    } else {
      const result = getStaffTabContent(menuType);
      setTabContents(result);
    }
  }, [role, menuType]);

  const tabContent = tabContents.map((item, index) => {
    return <TabButton key={index} index={index} item={item}></TabButton>;
  });

  return <div className={styles.tab}>{tabContent}</div>;
}
