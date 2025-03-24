import apiInstance from "./instance/apiInstance";

export const absenceAttendancesApi = {
  getAbsenceAttendancesByCourseId: async (courseId) => {
    const response = await apiInstance.get(
      `/api/course/${courseId}/absence-attendances`
    )

    return response
  }
}
