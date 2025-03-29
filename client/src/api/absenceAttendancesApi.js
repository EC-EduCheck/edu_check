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
};
