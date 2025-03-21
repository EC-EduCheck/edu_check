import { createSlice } from '@reduxjs/toolkit';
import { login, logout } from './authSlice';

const initialState = {
  nav: '출결',
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
    setRole: (state, action) => {
      state.nav = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login, (state, action) => {
        state.nav = action.payload.role === 'STUDENT' ? '출석' : '출결';
      })
      .addCase(logout, (state) => {
        state.nav = '';
      });
  },
});

export const { updateNav, updateTab, setRole } = sideBarItemSlice.actions;
export default sideBarItemSlice.reducer;
