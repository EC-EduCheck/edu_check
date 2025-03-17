import axios from 'axios';

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

api.interceptors.request.use((config) => {
  const accessToken = store.getState().auth.accessToken;
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }
  return config;
});

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const response = error.response;
    if (response.data?.code === 403) {
      try {
        const response = await api.reissue();
        const accessToken = response.headers.authorization;
        store.dispatch(login(accessToken));
        originalRequest.headers['Authorization'] = accessToken;
        return instance(originalRequest);
      } catch {
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  },
);

export default instance;
