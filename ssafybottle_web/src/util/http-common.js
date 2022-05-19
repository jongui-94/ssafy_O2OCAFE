import axios from 'axios';

// axios 객체 생성
export default axios.create({
  baseURL: 'http://175.198.147.122:9999/rest', // http://localhost:9999/rest
  headers: {
    'Content-type': 'application/json',
  },
});