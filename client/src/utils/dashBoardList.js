export const roleList = ['STUDENT', 'STAFF'];

const studentBaseUrl = '/dashBoard/student';
const staffBaseUrl = '/dashBoard/staff';

const studentRoutes = {
  출석: [`${studentBaseUrl}/attendance`, `${studentBaseUrl}/attendance/absence`],
  '회의실 예약': [`${studentBaseUrl}/reservation`],
  설정: [`${studentBaseUrl}/setting`],
};

export const getStudentPath = (nav, tab) => {
  return studentRoutes[nav]?.[tab] || `${studentBaseUrl}/attendance`;
};

const staffRoutes = {
  출결: [
    `${staffBaseUrl}/attendance`,
    `${staffBaseUrl}/attendance/detail`,
    `${staffBaseUrl}/attendance/absence`,
  ],
  '학습자 관리': [`${staffBaseUrl}/studentManage`],
  '회의실 예약': [`${staffBaseUrl}/reservation`],
};

export const getStaffPath = (nav, tab) => {
  return staffRoutes[nav]?.[tab] || `${staffBaseUrl}/attendance`;
};
