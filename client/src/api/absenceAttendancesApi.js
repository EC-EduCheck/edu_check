import apiInstance from "./instance/apiInstance";

export const absenceAttendancesApi = {
  getAbsenceAttendancesByCourseId: async (courseId) => {
    const response = await apiInstance.get(
      `/course/${courseId}/absence-attendances`
    )

    return response
  }
}
