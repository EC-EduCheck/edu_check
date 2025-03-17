import React from 'react'
import styles from "./DashBoard.module.css"
import Tab from '../../components/tab/Tab';
import SideBar from '../../components/sideBar/SideBar';

export default function DashBoard() {
  return (
    <div className="container">
      <Tab></Tab>
      <SideBar></SideBar>
    </div>
  );
}
