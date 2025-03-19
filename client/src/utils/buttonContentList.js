// MainButton
export const activeTitle = ['퇴근', '로그인'];

export const fullWidthTitle = ['퇴근', '출석하기', '로그인'];

// Tag
export const tagList = ['수강중', '수료', '수강 중단'];

export const getTagColors = (menuType) => {
  switch (true) {
    case ['출석', '수강중'].includes(menuType):
      return 'green';
    case ['지각', '조퇴', '수료'].includes(menuType):
      return 'yellow';
    case ['결석', '수강 중단'].includes(menuType):
      return 'red';
    default:
      return false;
  }
};

export const getIsClickable = (menuType) => {
  switch (true) {
    case ['출석', '지각', '조퇴', '결석'].includes(menuType):
      return false;
    case ['수강중', '수료', '수강 중단'].includes(menuType):
      return true;
    default:
      return false;
  }
};

// LeftLineListItem
export const getBackgroundColor = (menuType) => {
  switch (true) {
    case ['승인'].includes(menuType):
      return 'green';
    case ['반려'].includes(menuType):
      return 'red';
    default:
      return false;
  }
};
