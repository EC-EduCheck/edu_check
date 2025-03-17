import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  accessToken: '',
  isLoggedIn: false,
  user: {
    name: '',
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
    },
    logout: (state, action) => {
      state.accessToken = null;
      state.isLoggedIn = false;
      state.user.name = null;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;