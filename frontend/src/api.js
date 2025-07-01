// src/api.js

import axios from 'axios';

// API 클라이언트 인스턴스 생성
const apiClient = axios.create({
    // baseURL을 설정하면, 매번 주소 전체를 입력할 필요가 없습니다.
    // Vite 프록시를 사용하므로, 여기서는 상대 경로만 사용해도 됩니다.
    baseURL: '/', 
});

// 모든 책 목록을 가져오는 API
export const getBooks = () => {
    return apiClient.get('/books');
};

// 특정 ID의 책을 가져오는 API
export const getBookById = (id) => {
    return apiClient.get(`/books/${id}`);
};

// 새 책을 저장하는 API
export const createBook = (bookData) => {
    return apiClient.post('/books', bookData);
};

// 출간을 요청하는 API
export const requestPublication = (id) => {
    return apiClient.post(`/books/${id}/request-publication`);
};

// 책을 열람하는 API
export const openBook = (id) => {
    return apiClient.put(`/books/${id}/open`);
};

// 내가 구독한 책 목록 조회 API
export const getSubscribedBooks = (userId) => {
  return apiClient.get(`/books/search/my-subscriptions`, { params: { userId } });
};

// 베스트셀러 목록 조회 API
export const getBestsellers = () => {
  return apiClient.get('/books/bestsellers');
};