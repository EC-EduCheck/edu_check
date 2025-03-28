import axios from 'axios';
import store from '../../store/store';

const apiInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  // withCredentials: true,
});

const NO_TOKEN_REQUIRED = ['/api/auth/login', '/api/auth/signup', '/api/auth/refresh', '/'];

apiInstance.interceptors.request.use((config) => {
  // console.log(config);
  const accessToken = store.getState().auth.accessToken;
  const isRequiredTokenUrl = !NO_TOKEN_REQUIRED.includes(config.url);

  if (isRequiredTokenUrl) {
    config.headers['Authorization'] = `Bearer ${accessToken}`;
    // console.log(config.headers);
  }

  // console.log(config);

  return config;
});

// apiInstance.interceptors.response.use(
//   (response) => {
//     return response;
//   },
//   async (error) => {
//     const response = error.response;
//     if (response.data?.code === 403) {
//       try {
//         const response = await api.reissue();
//         const accessToken = response.headers.authorization;
//         store.dispatch(login(accessToken));
//         originalRequest.headers['Authorization'] = accessToken;
//         return apiInstance(originalRequest);
//       } catch {
//         return Promise.reject(error);
//       }
//     }

//     return Promise.reject(error);
//   },
// );

export default apiInstance;
