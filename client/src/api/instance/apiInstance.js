import axios from 'axios';
import store from '../../store/store';

const apiInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  // withCredentials: true,
});

const NO_TOKEN_REQUIRED = ['/api/auth/login', '/api/auth/signup', '/api/auth/refresh', '/'];

apiInstance.interceptors.request.use((config) => {
  const accessToken = store.getState().auth.accessToken;
  const isRequiredTokenUrl = !NO_TOKEN_REQUIRED.includes(config.url);

  if (isRequiredTokenUrl) {
    config.headers['Authorization'] = `Bearer ${accessToken}`;
  }

  return config;
});

export default apiInstance;
