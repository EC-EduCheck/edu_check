export const getBackgroundColor = (menuType) => {
  switch (true) {
    case ['미승인', '대기'].includes(menuType):
      return '#D9D9D9';
    case ['승인'].includes(menuType):
      return '#b3e56a';
    case ['반려'].includes(menuType):
      return '#e56a6e';
  }
};
