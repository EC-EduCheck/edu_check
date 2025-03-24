import apiInstance from './apiInstance';
import authInstance from './authInstance';

export const authApi = {
  login: async (email, password) => {
    const response = await authInstance.post(
      '/auth/login',
      {
        email: email,
        password: password,
      },
      {
        withCredentials: true,
      },
    );

    return response;
  },

  reissue: async () => {
    const response = await apiInstance.post(
      '/auth/refresh',
      {},
      {
        withCredentials: true,
        headers: {
          'Access-Control-Allow-Origin': `${import.meta.env.VITE_API_URL}`,
        },
      },
    );

    return response;
  },

  logout: async () => {
    const response = await apiInstance.post(
      '/auth/logout',
      {},
      {
        withCredentials: true,
      },
    );
    return response;
  },
};
