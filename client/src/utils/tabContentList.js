const attendanceContent = ['출석 현황', '유고 결석'];
const meetingRoomContent = ['회의실 예약'];
const settingContent = ['개인정보 수정'];

export const getTabContent = (menuType) => {
  switch (menuType) {
    case '출석':
      return attendanceContent;
    case '회의실 예약':
      return meetingRoomContent;
    case '설정':
      return settingContent;
  }
};
