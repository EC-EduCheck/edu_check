import { studentBaseUrl, staffBaseUrl } from './baseUrl';

export const tabList = {
  STUDENT: {
    attendance: [
      { name: '출석 현황', path: `${studentBaseUrl}/attendance` },
      { name: '유고 결석', path: `${studentBaseUrl}/attendance/absence` },
    ],
    reservation: [{ name: '회의실 예약', path: `${studentBaseUrl}/reservation` }],
    setting: [{ name: '개인정보 수정', path: `${studentBaseUrl}/setting` }],
  },

  MIDDLE_ADMIN: {
    attendance: [
      { name: '금일 출결 현황', path: `${staffBaseUrl}/attendance` },
      { name: '세부 출결 현황', path: `${staffBaseUrl}/attendance/detail` },
      { name: '유고 결석 관리', path: `${staffBaseUrl}/attendance/absence` },
    ],
    studentManage: [{ name: '학습자', path: '' }],
    reservation: [{ name: '회의실 예약', path: '' }],
  },
};
