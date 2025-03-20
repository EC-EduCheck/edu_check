import instance from './axios';

export const attendanceApi = {
  submitAttendance: async (latitude, longitude) => {
    const response = await instance.post(
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
