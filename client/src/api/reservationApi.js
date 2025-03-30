import apiInstance from './instance/apiInstance';

export const reservationApi = {
  getReservations: async (campusId) => {
    // /api/campuses/{campusId}/meeting-rooms/reservations
    const response = await apiInstance.get(`/campuses/${campusId}/meeting-rooms/reservations`);
    return response;
  },

  createReservation: async (campusId, requestBody) => {
    await apiInstance.post(`/campuses/${campusId}/meeting-rooms/reservations`, requestBody);
    return response;
  },

  cancelReservation: async (campusId, meetingRoomReservationId) => {
    await apiInstance.delete(
      `/campuses/${campusId}/meeting-rooms/reservations/${meetingRoomReservationId}`,
    );
    return response;
  },
};
