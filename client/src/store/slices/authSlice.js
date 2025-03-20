import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  accessToken: '',
  isLoggedIn: false,
  user: {
    name: '',
    role: 'STUDENT',
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
    },
    logout: (state, action) => {
      state.accessToken = null;
      state.isLoggedIn = false;
      state.user.name = null;
      state.user.role = null;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
