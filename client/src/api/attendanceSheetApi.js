import axios from 'axios';
import store from '../store/store';

const apiInstance = axios.create({
  baseURL: import.meta.env.VITE_APP_URL,
});

apiInstance.interceptors.request.use((config) => {
  const accessToken = store.getState().auth.accessToken;
  config.headers['Authorization'] = `Bearer ${accessToken}`;

  return config;
});

export const attendanceSheetApi = {
  getStudentAttendanceSheet: async (courseId, memberId) =>
    await apiInstance.get(`/courses/${courseId}/members/${memberId}`),

  getStudentAttendanceSheetFile: async (courseId, memberId) =>
    await apiInstance.get(`/courses/${courseId}/members/${memberId}/download`, {
      responseType: 'blob',
    }),
};
