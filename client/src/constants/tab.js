import { URL_PATHS } from './urlPaths';

export const tabList = {
  STUDENT: {
    attendance: [
      { name: '출석 현황', path: URL_PATHS.STUDENT.ATTENDANCE.BASE },
      { name: '유고 결석', path: URL_PATHS.STUDENT.ATTENDANCE.ABSENCE },
    ],
    reservation: [{ name: '회의실 예약', path: URL_PATHS.STUDENT.RESERVATION }],
    setting: [{ name: '개인정보 수정', path: URL_PATHS.STUDENT.SETTING }],
  },

  MIDDLE_ADMIN: {
    attendance: [
      { name: '금일 출결 현황', path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.BASE },
      { name: '세부 출결 현황', path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.DETAIL },
      { name: '유고 결석 관리', path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.ABSENCE },
    ],
    studentManage: [{ name: '학습자', path: URL_PATHS.MIDDLE_ADMIN.STUDENT_MANAGE }],
    reservation: [{ name: '회의실 예약', path: URL_PATHS.MIDDLE_ADMIN.RESERVATION }],
  },
};
