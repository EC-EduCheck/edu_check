import { createSlice } from '@reduxjs/toolkit';
import { jwtDecode } from 'jwt-decode';

const initialState = {
  accessToken: '',
  isLoggedIn: false,
  user: {
    role: '',
    name: '',
    birthDate: '',
    campusId: '',
    courseId: '',
    courseName: '',
    email: '',
    phoneNumber: '',
    lastLoginDate: '',
  },
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action) => {
      const accessToken = action.payload.accessToken.replace('Bearer ', '');
      const decodedToken = jwtDecode(accessToken);
      state.accessToken = accessToken;
      state.isLoggedIn = true;
      state.user = {
        role: decodedToken.roles[0] || '',
        email: decodedToken.sub || '',
        name: action.payload.name || '',
        birthDate: action.payload.birthDate || '',
        campusId: action.payload.campusId || '',
        courseId: action.payload.courseId || '',
        courseName: action.payload.courseName || '',
        phoneNumber: action.payload.phoneNumber || '',
        lastLoginDate: action.payload.lastLoginDate || '',
      };
    },
    logout: (state, action) => {
      state.accessToken = '';
      state.isLoggedIn = false;
      state.user = { ...initialState.user };
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
