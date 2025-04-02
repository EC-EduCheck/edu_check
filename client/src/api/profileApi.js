import apiInstance from './instance/apiInstance';

export const profileApi = {
  getMyProfile: async () => {
    return await apiInstance.get(`/my`);
  },
};
