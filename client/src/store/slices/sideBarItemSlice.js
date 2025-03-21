import { createSlice } from '@reduxjs/toolkit';
import { login, logout } from './authSlice';
import { jwtDecode } from 'jwt-decode';

const initialState = {
  nav: '',
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
        const accessToken = action.payload.accessToken.replace('Bearer ', '');
        const decodedToken = jwtDecode(accessToken);
        const role = decodedToken.roles[0];
        state.nav = role === 'STUDENT' ? '출석' : '출결';
      })
      .addCase(logout, (state) => {
        state.nav = '';
      });
  },
});

export const { updateNav, updateTab, setRole } = sideBarItemSlice.actions;
export default sideBarItemSlice.reducer;
