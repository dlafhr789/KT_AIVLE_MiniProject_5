<script setup>
import { ref, onMounted } from 'vue';
// 1. 새로 만든 API 함수를 import 합니다.
import { getBooks, createBook, getSubscribedBooks, getBestsellers } from './api';

// --- 상태(State) 정의 ---
const allBooks = ref([]);
const newBook = ref({
  title: '',
  content: '',
  userId: 1
});
// 2. 구독한 책 목록을 담을 새로운 상태 변수를 추가합니다.
const subscribedBooks = ref([]);


// --- 함수(Methods) 정의 ---
const fetchAllBooks = async () => {
  try {
    const response = await getBooks();
    allBooks.value = response.data._embedded ? response.data._embedded.books : [];
  } catch (error) {
    console.error("전체 책 목록을 불러오는 데 실패했습니다:", error);
  }
};

const fetchBestsellers = async () => {
  try {
    const response = await getBestsellers();
    bestsellers.value = response.data;
  } catch (error) {
    console.error("베스트셀러 목록을 불러오는 데 실패했습니다:", error);
  }
};

const handleSubmit = async () => {
  if (!newBook.value.title || !newBook.value.content) {
    alert('제목과 내용을 모두 입력해주세요.');
    return;
  }
  try {
    const response = await createBook(newBook.value);
    allBooks.value.unshift(response.data);
    alert('새 책이 성공적으로 저장되었습니다!');
    newBook.value.title = '';
    newBook.value.content = '';
  } catch (error) {
    console.error("책을 저장하는 데 실패했습니다:", error);
  }
};

// 3. '내 구독 목록 불러오기' 버튼을 클릭했을 때 실행될 함수를 새로 추가합니다.
const fetchMySubscriptions = async () => {
  try {
    const userId = 1; // 임시로 사용자 ID 1번으로 테스트
    const response = await getSubscribedBooks(userId);
    subscribedBooks.value = response.data;
  } catch (error) {
    console.error("구독한 책 목록을 불러오는 데 실패했습니다:", error);
  }
};

onMounted(() => {
  fetchAllBooks();
  fetchBestsellers();
});
</script>

<template>
  <div id="app">
    <h1>통합 도서 관리</h1>

    <div>
      <h2>베스트셀러 TOP 5 👑</h2>
      <ol>
        <li v-for="book in bestsellers" :key="book.id">
          <strong>{{ book.title }}</strong> (구독자: {{ book.subscribers }}명)
        </li>
      </ol>
    </div>

    <form @submit.prevent="handleSubmit">
      <h2>새 책 저장하기</h2>
      <div>
        <label for="title">제목: </label>
        <input type="text" id="title" v-model="newBook.title" />
      </div>
      <div>
        <label for="content">내용: </label>
        <textarea id="content" v-model="newBook.content"></textarea>
      </div>
      <button type="submit">저장</button>
    </form>

    <hr />

    <div>
      <h2>내가 구독한 책 목록</h2>
      <button @click="fetchMySubscriptions">구독 목록 불러오기 (사용자 1)</button>
      <ul v-if="subscribedBooks.length > 0">
        <li v-for="book in subscribedBooks" :key="book.id">
          <strong>{{ book.title }}</strong>
        </li>
      </ul>
      <p v-else>구독한 책이 없거나 아직 불러오지 않았습니다.</p>
    </div>

    <hr />
    
    <h2>전체 책 목록</h2>
    <ul>
      <li v-for="book in allBooks" :key="book.id">
        <strong>{{ book.title }}</strong> (조회수: {{ book.view }})
      </li>
    </ul>
  </div>
</template>

<style>
#app { font-family: sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }
form { border: 1px solid #ccc; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
form div, h2 { margin-bottom: 10px; }
label { display: inline-block; width: 50px; }
input, textarea { width: 300px; padding: 5px; }
hr { margin: 20px 0; }
</style>