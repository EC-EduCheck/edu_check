// sidebar
export const studentSideBarList = ['출석', '회의실 예약', '설정'];

export const studentSideBarIconList = [
  '../../assets/calender-icon.png',
  '../../assets/alarm-icon.png',
  '../../assets/setting-icon.png',
];

export const staffSideBarList = ['출결', '학습자 관리', '회의실 예약'];

export const staffSideBarIconList = [
  '../../assets/calender-icon.png',
  '../../assets/check-icon.png',
  '../../assets/setting-icon.png',
];

// tab
const studentAttendanceContent = ['출석 현황', '유고 결석'];
const studentMeetingRoomContent = ['회의실 예약'];
const studentSettingContent = ['개인정보 수정'];

export const getStudentTabContent = (menuType) => {
  switch (menuType) {
    case studentSideBarList[0]:
      return studentAttendanceContent;
    case studentSideBarList[1]:
      return studentMeetingRoomContent;
    case studentSideBarList[2]:
      return studentSettingContent;
  }
};

const staffAttendanceContent = ['금일 출결 현황', '세부 출결 현황', '유고 결석 관리'];
const staffStudentManageContent = ['학습자'];
const staffMeetingRoomContent = ['회의실 예약'];

export const getStaffTabContent = (menuType) => {
  switch (menuType) {
    case staffSideBarList[0]:
      return staffAttendanceContent;
    case staffSideBarList[1]:
      return staffStudentManageContent;
    case staffSideBarList[2]:
      return staffMeetingRoomContent;
  }
};
