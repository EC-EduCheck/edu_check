import apiInstance from './instance/apiInstance';

export const absenceAttendancesApi = {
  getAbsenceAttendancesByCourseId: async (courseId) =>
    await apiInstance.get(`/course/${courseId}/absence-attendances`),

  processAbsenceAttendance: async (courseId, absenceAttendancedId, isApproved) =>
    await apiInstance.post(`/course/${courseId}/absence-attendances/${absenceAttendancedId}`, {
      isApproved: isApproved,
    }),
};
