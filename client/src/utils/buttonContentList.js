export const getBgColor = (title) => {
  switch (true) {
    case ['퇴근', '로그인'].includes(title):
      return '#B3E56A';
    case ['로그아웃'].includes(title):
      return '#EFEFEF';
    default:
      return '#FFFFFF';
  }
};

export const getWidth = (title) => {
  switch (true) {
    case ['출석하기', '퇴근'].includes(title):
      return '100%';
    default:
      return 'auto';
  }
};
