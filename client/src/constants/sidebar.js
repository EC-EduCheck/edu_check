import { studentBaseUrl, staffBaseUrl } from './baseUrl';
// sidebar

export const sidebarList = {
  STUDENT: [
    { name: '출석', icon: '/assets/calender-icon.png', path: `${studentBaseUrl}/attendance` },
    { name: '회의실 예약', icon: '/assets/alarm-icon.png', path: `${studentBaseUrl}/reservation` },
    { name: '설정', icon: '/assets/alarm-icon.png', path: `${studentBaseUrl}/setting` },
  ],

  MIDDLE_ADMIN: [
    { name: '출결', icon: '/assets/calender-icon.png', path: `${staffBaseUrl}/attendance` },
    { name: '학습자 관리', icon: '/assets/check-icon.png', path: `${staffBaseUrl}/studentManage` },
    { name: '회의실 예약', icon: '/assets/alarm-icon.png', path: `${staffBaseUrl}/reservation` },
  ],
};
