import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  accessToken: '',
  isLoggedIn: false,
  user: {
    name: '홍길동',
    role: 'STUDENT',
    courseId: '1',
    courseName: '클라우드 기반 JAVA 풀스택 웹개발',
    phoneNumber: '010-1234-1234',
    birthDate: '1958.10.31',
    email: 'rlfehd123@naver.com',
  },
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.isLoggedIn = true;
      state.user.name = action.payload.username;
      state.user.role = action.payload.role;
      state.user.courseId = action.payload.courseId;
      state.user.courseName = action.payload.courseName;
      state.user.phoneNumber = action.payload.phoneNumber;
      state.user.birthDate = action.payload.birthDate;
      state.user.email = action.payload.email;
    },
    logout: (state, action) => {
      state.accessToken = null;
      state.isLoggedIn = false;
      state.user.name = null;
      state.user.role = null;
      state.user.courseId = null;
      state.user.courseName = null;
      state.user.phoneNumber = null;
      state.user.birthDate = null;
      state.user.email = null;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
