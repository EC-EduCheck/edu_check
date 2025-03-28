import apiInstance from './instance/apiInstance';

export const attendanceApi = {
  submitAttendance: async (latitude, longitude) => {
    const response = await apiInstance.post(
      '/checkin',
      {
        longitude,
        latitude,
      },
      {
        withCredentials: true,
      },
    );
    return response.data;
  },
  submitCheckOut: async () => {
    const response = await apiInstance.patch(
      '/checkout',
      {},
      {
        withCredentials: true,
      },
    );
    return response.data;
  },
  // STAFF
  getTodayAttendances: async (courseId) => {
    const response = await apiInstance.get(
      `/courses/${courseId}/attendances/today`,
      {},
      {
        withCredentials: true,
      },
    );
    return response;
  },
  getStudentAttendances: async (courseId, studentId) => {
    const response = await apiInstance.get(
      `/courses/${courseId}/students/${studentId}/attendances`,
      {},
      {
        withCredentials: true,
      },
    );
    return response;
  },
};
