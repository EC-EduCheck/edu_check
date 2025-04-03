import apiInstance from './instance/apiInstance';

export const absenceAttendancesApi = {
  getAbsenceAttendancesByCourseId: async (courseId, page = 0) =>
    await apiInstance.get(`/course/${courseId}/absence-attendances?page=${page}`),

  processAbsenceAttendance: async (courseId, absenceAttendancedId, isApproved) =>
    await apiInstance.patch(`/course/${courseId}/absence-attendances/${absenceAttendancedId}`, {
      isApprove: isApproved,
    }),

  getAbsenceAttendance: async (courseId, absenceAttendancedId) =>
    await apiInstance.get(`/course/${courseId}/absence-attendances/${absenceAttendancedId}`),

  getAbsenceAttendanceListByStudent: async (courseId) =>
    await apiInstance.get(`/my/course/${courseId}/absence-attendances`),

  submitAbsenceAttendance: async (courseId, absenceData) => {
    console.log('API 요청 데이터:', absenceData);
    console.log('file 객체:', absenceData.file);
    await apiInstance.post(`${baseURL}/my/course/${courseId}/absence-attendances`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
};
