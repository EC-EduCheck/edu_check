import { createAction, createSlice } from '@reduxjs/toolkit';
import { login, logout } from './authSlice';
import { jwtDecode } from 'jwt-decode';

import {
  studentSideBarList,
  staffSideBarList,
  studentSideBarIconList,
  staffSideBarIconList,
} from '../../utils/sideBarList';

import { getStudentTabContent, getStaffTabContent } from '../../utils/tabContentList';

export const updateNav = createAction('sideBarItem/updateNav');

const initialState = {
  nav: '',
  tab: 0,
  sidebarItemList: [],
  sidebarIconList: [],
  tabContentsList: [],
  role: null,
};

const sideBarItemSlice = createSlice({
  name: 'sideBarItem',
  initialState,
  reducers: {
    updateTab: (state, action) => {
      state.tab = action.payload;
    },
    setRole: (state, action) => {
      state.nav = action.payload;
      state.sidebarItemList = action.payload;
      state.sidebarIconList = action.payload;
      state.tabContentsList = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login, (state, action) => {
        const accessToken = action.payload.accessToken.replace('Bearer ', '');
        const decodedToken = jwtDecode(accessToken);
        const role = decodedToken.roles[0];

        state.role = role;
        state.nav = role === 'STUDENT' ? '출석' : '출결';
        state.sidebarItemList = role === 'STUDENT' ? studentSideBarList : staffSideBarList;
        state.sidebarIconList = role === 'STUDENT' ? studentSideBarIconList : staffSideBarIconList;
        state.tabContentsList =
          role === 'STUDENT' ? getStudentTabContent(state.nav) : getStaffTabContent(state.nav);
      })
      .addCase(logout, (state) => {
        state.role = null;
        state.nav = '';
        state.sidebarItemList = [];
        state.sidebarIconList = [];
        state.tabContentsList = [];
      })
      .addCase(updateNav, (state, action) => {
        if (state.role) {
          state.nav = action.payload;
          state.tabContentsList =
            state.role === 'STUDENT'
              ? getStudentTabContent(state.nav)
              : getStaffTabContent(state.nav);
        }
      });
  },
});

export const { updateTab, setRole } = sideBarItemSlice.actions;
export default sideBarItemSlice.reducer;
