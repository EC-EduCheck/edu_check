import api from './axios';

export default api = {
  login: async (email, password) => {
    const response = await api.post(
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
    const response = await api.post(
      '/auth/reissue',
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
    const response = await api.post(
      '/auth/logout',
      {},
      {
        withCredentials: true,
      },
    );
    return response;
  },
};
