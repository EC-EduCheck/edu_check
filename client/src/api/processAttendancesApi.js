import apiInstance from './instance/apiInstance';

export const processAttendancesApi = {
  getAbsenceAttendancesByCourseId: async (courseId) => {
    const response = await apiInstance.get(`/api/course/${courseId}/absence-attendances`, {
      withCredentials: true,
    });

    return response;
  },
};

export default processAttendancesApi;