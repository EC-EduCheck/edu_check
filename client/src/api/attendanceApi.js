import apiInstance from './apiInstance';

export const attendanceApi = {
  submitAttendance: async (latitude, longitude) => {
    const response = await apiInstance.post(
      '/checkin',
      {
        longitude,
        latitude,
      },
      {
        withCredentials: true,
      },
    );
    console.log(response);
    return response.data;
  },
};
