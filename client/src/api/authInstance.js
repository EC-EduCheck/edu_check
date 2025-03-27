import axios from 'axios';

const authInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

export default authInstance;
