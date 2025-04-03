import apiInstance from './instance/apiInstance';

export const studentManageApi = {
  getStudentList: async (courseId) => await apiInstance.get(`/course/${courseId}/students`),

  registerNewStudent: async (studentData) => await apiInstance.post('/auth/signup', studentData),

  modifyStudentStatus: async (courseId, studentId, status) =>
    await apiInstance.put(`/course/${courseId}/students/${studentId}`, { status: status }),
};
