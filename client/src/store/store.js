import { configureStore } from '@reduxjs/toolkit';
import authReduce from './slices/authSlice';
import sideBarItemReduce from './slices/sideBarItemSlice';

const store = configureStore({
  reducer: {
    auth: authReduce,
    sideBarItem: sideBarItemReduce,
  },
});

export default store;
