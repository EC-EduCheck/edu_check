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
  getAbsenceAttendanceAndRate: async (courseId) => {
    const response = await apiInstance.get(
      `/my/course/${courseId}/attendances/stats`,
      {},
      {
        withCredentials: true,
      },
    );
    return response.data;
  },
  getAttendanceRecords: async (courseId, page = 0, size = 10) => {
    const response = await apiInstance.get(
      `/my/courses/${courseId}/attendances?page=${page}&size=${size}`,
      {},
      {
        withCredentials: true,
      },
    );
    return response.data;
  },
  getAttendanceRecordsByYearMonth: async (courseId, year, month, page = 0, size = 10) => {
    const response = await apiInstance.get(
      `/my/courses/${courseId}/attendances?year=${year}&month=${month}&page=${page}&size=${size}`,
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
