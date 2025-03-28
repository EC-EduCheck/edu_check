import React, { use, useEffect } from 'react';
import styles from './Home.module.css';
import { useSelector } from 'react-redux';
import DashBoard from '../dashBoard/DashBoard';
import Login from '../login/Login';
import { Navigate, useNavigate } from 'react-router-dom';

export default function Home() {
  const { isLoggedIn } = useSelector((state) => state.auth);
  const { user } = useSelector((state) => state.auth.user);
  const naviate = useNavigate();
  useEffect(() => {
    if (!isLoggedIn) {
      console.log('not loged in ');
      naviate('/login', { replace: true });
    } else {
      const role = user?.role;
      console.log(user);
    }
  }, [isLoggedIn]);

  // return isLoggedIn ? <Navigate to="/dashBoard" replace /> : <Navigate to="/login" replace />;
}
