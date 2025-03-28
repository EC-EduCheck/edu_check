import React from 'react';
import Tab from './tab/Tab';
export default function TmpLayout() {
  return (
    <>
      <div className={styles.dashBoardBox}>
        <Tab />

        <div className={styles.dashBoardContent}>
          <Outlet></Outlet>
        </div>
      </div>
    </>
  );
}
