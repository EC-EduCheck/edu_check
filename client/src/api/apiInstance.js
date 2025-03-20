import axios from 'axios';

const apiInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

// apiInstance.interceptors.request.use((config) => {
//   const accessToken = store.getState().auth.accessToken;
//   if (accessToken) {
//     config.headers.Authorization = `Bearer ${accessToken}`;
//   }
//   return config;
// });

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
