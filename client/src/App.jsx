import React, { useEffect } from 'react';
import { RouterProvider } from 'react-router-dom';
import router from './router';
import { authApi } from './api/authApi';
import { login } from './store/slices/authSlice';
import { useDispatch } from 'react-redux';
import { setRole } from './store/slices/sideBarItemSlice';

export default function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchRefreshToken = async () => {
      try {
        const response = await authApi.reissue();
        const accessToken = response.headers?.authorization ?? '';
        dispatch(
          login({
            ...response.data.data,
            accessToken: accessToken,
          }),
          setRole(response.data.data),
        );
      } catch (error) {
        console.error(error);
      }
    };

    fetchRefreshToken();
  }, []);

  return (
    <>
      <RouterProvider router={router}></RouterProvider>
    </>
  );
}
