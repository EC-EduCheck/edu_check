import axios from 'axios';
import apiInstance from './apiInstance';

export const reservationApi = {
  getReservations: async () => {
    const response = await apiInstance.get(`/campuses/1/meeting-rooms/reservations`);
    return response;
  },
};
