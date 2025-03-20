const studentAttendanceContent = ['출석 현황', '유고 결석'];
const studentMeetingRoomContent = ['회의실 예약'];
const studentSettingContent = ['개인정보 수정'];

export const getStudentTabContent = (menuType) => {
  switch (menuType) {
    case '출석':
      return studentAttendanceContent;
    case '회의실 예약':
      return studentMeetingRoomContent;
    case '설정':
      return studentSettingContent;
  }
};

const staffAttendanceContent = ['금일 출결 현황', '세부 출결 현황', '유고 결석 관리'];
const staffStudentManageContent = ['학습자'];
const staffMeetingRoomContent = ['회의실 예약'];

export const getStaffTabContent = (menuType) => {
  switch (menuType) {
    case '출결':
      return staffAttendanceContent;
    case '학습자 관리':
      return staffStudentManageContent;
    case '회의실 예약':
      return staffMeetingRoomContent;
  }
};
