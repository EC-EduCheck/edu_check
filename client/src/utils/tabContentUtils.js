const attendanceContent = ['출석 현황', '유고 결석'];

const meetingRoomContent = ['회의실 예약'];

export const getTabContent = (menuType) => {
  switch (menuType) {
    case 'attendance':
      return attendanceContent;
    case 'meetingRoom':
      return meetingRoomContent;
  }
};
