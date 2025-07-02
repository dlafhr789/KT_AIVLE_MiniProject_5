import axios from 'axios';

// 👉 하나만 만들어서 내보냅니다
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,   // .env 값
  withCredentials: true,                        // 필요 시
  // timeout, headers 등등 공통 옵션도 여기!
});

export default api;