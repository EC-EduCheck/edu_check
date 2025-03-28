import apiInstance from './instance/apiInstance';

export const reservationApi = {
  getReservations: async () => {
    const response = await apiInstance.get(`/campuses/1/meeting-rooms/reservations`);
    return response;
  },
};
