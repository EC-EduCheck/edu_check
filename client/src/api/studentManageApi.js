import apiInstance from './instance/apiInstance';

export const studentManageApi = {
  getStudentList: async (courseId) => await apiInstance.get(`/course/${courseId}/students`),
};
