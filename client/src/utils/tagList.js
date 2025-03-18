export const tagList = ['수강중', '수료', '수강 중단'];

export const getTagColors = (menuType) => {
  switch (true) {
    case ['출석', '수강중'].includes(menuType):
      return ['#EEF7E6', '#57DD79'];
    case ['지각', '조퇴', '수료'].includes(menuType):
      return ['#FFEA97', '#FFB72E'];
    case ['결석', '수강 중단'].includes(menuType):
      return ['#FFD9DA', '#E56A6E'];
    default:
      return ['#FFFFFF', '#000000'];
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
