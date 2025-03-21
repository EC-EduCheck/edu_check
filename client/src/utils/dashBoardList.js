export const roleList = ['STUDENT', 'STAFF'];

const studentBaseUrl = '/dashBoard/student';
const staffBaseUrl = '/dashBoard/staff';

export const studentNavList = [
  `${studentBaseUrl}/attendance`,
  `${studentBaseUrl}/reservation`,
  `${studentBaseUrl}/setting`,
];

export const getStudentTabList = (nav, tab) => {
  switch (nav) {
    case '출석':
      if (tab == 1) {
        return 'absence';
      }
  }
};

export const staffNavList = [
  `${staffBaseUrl}/attendance`,
  `${staffBaseUrl}/studentManage`,
  `${staffBaseUrl}/reservation`,
];

export const getStaffTabList = (nav, tab) => {
  switch (nav) {
    case '출결':
      if (tab == 1) {
        return 'detail';
      } else if (tab == 2) {
        return 'absence';
      }
  }
};
