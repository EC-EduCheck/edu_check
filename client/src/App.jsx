import React, { useEffect } from 'react';
import { RouterProvider } from 'react-router-dom';
import router from './router';
import { authApi } from './api/authApi';
import { login } from './store/slices/authSlice';
import { useDispatch } from 'react-redux';

export default function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchRefreshToken = async () => {
      try {
        const response = await authApi.reissue();
        console.log(response);
        dispatch(login(response));
      } catch (error) {
        console.error(error);
      }
    };
  }, []);

  return (
    <>
      <RouterProvider router={router}></RouterProvider>
    </>
  );
}
