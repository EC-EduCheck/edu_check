// MainButton
export const activeTitle = ['퇴근', '로그인'];

export const fullWidthTitle = ['퇴근', '출석하기', '로그인'];

// Tag
export const tagList = ['수강중', '수료', '수강 중단'];

const tagColors = {
  green: ['출석', '수강중'],
  yellow: ['지각', '조퇴', '수료'],
  red: ['결석', '수강 중단'],
};

export const getTagColors = (menuType) => {
  return Object.entries(tagColors).find(([color, types]) => types.includes(menuType))?.[0] || false;
};

const clickableList = {
  false: ['출석', '지각', '조퇴', '결석'],
  true: ['수강중', '수료', '수강 중단'],
};

export const getIsClickable = (menuType) => {
  return (
    Object.entries(clickableList).find(([click, types]) => types.includes(menuType))?.[0] || false
  );
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
