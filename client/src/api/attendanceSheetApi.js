import axios from 'axios';

const apiInstance = axios.create({
  baseURL: import.meta.env.VITE_APP_URL,
});

apiInstance.interceptors.request.use((config) => {
  const accessToken = store.getState().auth.accessToken;
  config.headers['Authorization'] = `Bearer ${accessToken}`;

  return config;
});

export const attendanceSheetApi = {
  getStudentAttendanceSheet: async (courseId, memberId) => {
    const response = await apiInstance.get(`/courses/${courseId}/members/${memberId}`);
    return response;
  },
};
