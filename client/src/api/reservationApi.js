import apiInstance from './instance/apiInstance';

export const reservationApi = {
  getReservations: async (campusId, formattedDate) => {
    // /api/campuses/{campusId}/meeting-rooms/reservations
    const response = await apiInstance.get(`/campuses/${campusId}/meeting-rooms/reservations`,{
      params: {date: formattedDate}
    });
    return response;
  },

  createReservation: async (campusId, requestBody) => {
    const response = await apiInstance.post(
      `/campuses/${campusId}/meeting-rooms/reservations`,
      requestBody,
    );
    return response;
  },

  cancelReservation: async (campusId, meetingRoomReservationId) => {
    const response = await apiInstance.delete(
      `/campuses/${campusId}/meeting-rooms/reservations/${meetingRoomReservationId}`,
    );
    return response;
  },
};
