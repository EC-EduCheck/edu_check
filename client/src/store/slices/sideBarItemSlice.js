import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  nav: '출석',
  tab: 0,
};

const sideBarItemSlice = createSlice({
  name: 'sideBarItem',
  initialState,
  reducers: {
    updateNav: (state, action) => {
      state.nav = action.payload;
      state.tab = 0;
    },
    updateTab: (state, action) => {
      state.tab = action.payload;
    },
  },
});

export const { updateNav, updateTab } = sideBarItemSlice.actions;
export default sideBarItemSlice.reducer;
