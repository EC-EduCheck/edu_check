import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  current: '',
};

const sideBarItemSlice = createSlice({
  name: 'sideBarItem',
  initialState,
  reducers: {
    update: (state, action) => {
      state.current = action.payload;
    },
  },
});

export const { update } = sideBarItemSlice.actions;
export default sideBarItemSlice.reducer;
