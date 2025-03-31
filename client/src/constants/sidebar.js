import { URL_PATHS } from './urlPaths';

export const sidebarList = {
  STUDENT: [
    { name: '출석', icon: '/assets/calender-icon.png', path: URL_PATHS.STUDENT.ATTENDANCE.BASE },
    { name: '회의실 예약', icon: '/assets/alarm-icon.png', path: URL_PATHS.STUDENT.RESERVATION },
    { name: '설정', icon: '/assets/setting-icon.png', path: URL_PATHS.STUDENT.SETTING },
  ],

  MIDDLE_ADMIN: [
    {
      name: '출결',
      icon: '/assets/calender-icon.png',
      path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.BASE,
    },
    {
      name: '학습자 관리',
      icon: '/assets/check-icon.png',
      path: URL_PATHS.MIDDLE_ADMIN.STUDENT_MANAGE,
    },
    {
      name: '회의실 예약',
      icon: '/assets/alarm-icon.png',
      path: URL_PATHS.MIDDLE_ADMIN.RESERVATION,
    },
  ],
};
