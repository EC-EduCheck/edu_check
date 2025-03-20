import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  accessToken: '',
  isLoggedIn: false,
  user: {
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
      state.accessToken = action.payload.accessToken;
      state.isLoggedIn = true;
      state.user = {
        name: action.payload.name || '',
        birthDate: action.payload.birthDate || '',
        campusId: action.payload.campusId || '',
        courseId: action.payload.courseId || '',
        courseName: action.payload.courseName || '',
        email: action.payload.email || '',
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
