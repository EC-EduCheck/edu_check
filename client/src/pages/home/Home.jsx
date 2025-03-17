import React from 'react'
import styles from "./Home.module.css";
import { useSelector } from 'react-redux';
import DashBoard from '../dashBoard/DashBoard';
import Login from '../login/Login';

export default function Home() {
  const { isLoggedIn } = useSelector((state) => state.auth);

  // return isLoggedIn ? <DashBoard /> : <Login />;
  return <DashBoard></DashBoard>;
}
