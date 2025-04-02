export const BASE_PATHS = {
  STUDENT: '/dashBoard/student',
  MIDDLE_ADMIN: '/dashBoard/staff',
};

export const URL_PATHS = {
  STUDENT: {
    BASE: BASE_PATHS.STUDENT,
    ATTENDANCE: {
      BASE: `${BASE_PATHS.STUDENT}/attendance`,
      ABSENCE: `${BASE_PATHS.STUDENT}/attendance/absence`,
    },
    RESERVATION: `${BASE_PATHS.STUDENT}/reservation`,
    SETTING: `${BASE_PATHS.STUDENT}/setting`,
  },
  MIDDLE_ADMIN: {
    BASE: BASE_PATHS.MIDDLE_ADMIN,
    ATTENDANCE: {
      BASE: `${BASE_PATHS.MIDDLE_ADMIN}/attendance`,
      DETAIL_PATTERN: `${BASE_PATHS.MIDDLE_ADMIN}/attendance/course/:courseId/student/:studentId`,
      DETAIL: (courseId, studentId) =>
        `${BASE_PATHS.MIDDLE_ADMIN}/attendance/course/${courseId}/student/${studentId}`,
      ABSENCE: `${BASE_PATHS.MIDDLE_ADMIN}/attendance/absence`,
    },
    STUDENT_MANAGE: `${BASE_PATHS.MIDDLE_ADMIN}/studentManage`,
    RESERVATION: `${BASE_PATHS.MIDDLE_ADMIN}/reservation`,
  },
};
