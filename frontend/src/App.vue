<script setup>
import { ref, onMounted } from 'vue';
import { getBooks, createBook, getMySubscriptions } from './api';

// --- 상태(State) 정의 ---
// 전체 책 목록을 담을 배열
const allBooks = ref([]);
// 내가 구독한 책 목록을 담을 배열
const subscribedBooks = ref([]);
// 새 책 저장을 위한 폼 데이터
const newBook = ref({
  title: '',
  content: '',
  userId: 1 // 임시로 userId를 1로 고정
});

// --- 함수(Methods) 정의 ---
// (1) 전체 책 목록을 가져오는 함수
const fetchAllBooks = async () => {
  try {
    const response = await getBooks();
    allBooks.value = response.data._embedded ? response.data._embedded.books : [];
  } catch (error) {
    console.error("전체 책 목록을 불러오는 데 실패했습니다:", error);
  }
};

// (2) 새 책 저장 폼을 제출하는 함수
const handleSubmit = async () => {
  if (!newBook.value.title || !newBook.value.content) {
    alert('제목과 내용을 모두 입력해주세요.');
    return;
  }
  try {
    // API 호출 후, 응답으로 받은 새 책 데이터를 목록에 바로 추가 (UI 즉시 업데이트)
    const response = await createBook(newBook.value);
    allBooks.value.unshift(response.data);

    alert('새 책이 성공적으로 저장되었습니다!');
    newBook.value.title = '';
    newBook.value.content = '';
  } catch (error) {
    console.error("책을 저장하는 데 실패했습니다:", error);
  }
};

// (3) 내가 구독한 책 목록을 가져오는 함수
const fetchMySubscriptions = async () => {
  try {
    const userId = 1; // 임시로 사용자 ID 1번으로 테스트
    const response = await getMySubscriptions(userId);
    subscribedBooks.value = response.data._embedded.mySubscriptions;
    if (subscribedBooks.value.length === 0) {
      alert('사용자 ' + userId + '의 구독 정보가 없거나, 아직 테스트 데이터를 만들지 않았습니다.');
    }
  } catch (error) {
    console.error("구독한 책 목록을 불러오는 데 실패했습니다:", error);
  }
};

// 컴포넌트가 처음 화면에 나타날 때 전체 책 목록을 가져옵니다.
onMounted(() => {
  fetchAllBooks();
});
</script>

<template>
  <div id="app">
    <h1>통합 도서 관리</h1>

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
        <li v-for="sub in subscribedBooks" :key="sub.bookId">
          <strong>{{ sub.bookTitle }}</strong>
        </li>
      </ul>
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