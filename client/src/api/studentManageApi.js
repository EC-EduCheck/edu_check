import apiInstance from './instance/apiInstance';

export const studentManageApi = {
  getStudentList: async (courseId) => await apiInstance.get(`/course/${courseId}/students`),

  registerNewStudent: async (studentData) => await apiInstance.post('/auth/signup', studentData),
};
