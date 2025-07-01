<script setup>
import { ref, onMounted } from 'vue';
// 3단계에서 만든 API 함수들을 가져옵니다.
import { getBooks, createBook } from './api';

// 화면에 보여줄 책 목록 (반응형 상태)
const books = ref([]);

// 새 책 저장을 위한 폼 데이터 (반응형 상태)
const newBook = ref({
  title: '',
  content: '',
  userId: 1 // 임시로 userId를 1로 고정
});

// 모든 책 목록을 가져와서 `books` 상태에 저장하는 함수
const fetchBooks = async () => {
  try {
    const response = await getBooks();
    // Spring Data REST는 _embedded.books 안에 목록이 담겨있습니다.
    books.value = response.data._embedded.books; 
  } catch (error) {
    console.error("책 목록을 불러오는 데 실패했습니다:", error);
  }
};

// 새 책 저장 폼을 제출했을 때 실행될 함수
const handleSubmit = async () => {
  if (!newBook.value.title || !newBook.value.content) {
    alert('제목과 내용을 모두 입력해주세요.');
    return;
  }
  try {
    // 1. createBook API는 성공 시 생성된 book 객체를 응답으로 반환합니다.
    const response = await createBook(newBook.value);
    
    // 2. 목록 전체를 다시 불러오는 대신, 응답으로 받은 새 책 데이터를
    //    기존 books 배열의 맨 앞에 직접 추가합니다. (UI가 즉시 업데이트됩니다)
    books.value.unshift(response.data);

    alert('새 책이 성공적으로 저장되었습니다!');
    
    // 3. 폼 입력 내용을 초기화합니다.
    newBook.value.title = '';
    newBook.value.content = '';
    
  } catch (error) {
    console.error("책을 저장하는 데 실패했습니다:", error);
    alert('책 저장에 실패했습니다.');
  }
};

// 컴포넌트가 처음 화면에 마운트될 때 책 목록을 가져옵니다.
onMounted(() => {
  fetchBooks();
});
</script>

<template>
  <div id="app">
    <h1>나의 서재</h1>

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

    <h2>책 목록</h2>
    <ul>
      <li v-for="book in books" :key="book.id">
        <strong>{{ book.title }}</strong> (조회수: {{ book.view }})
      </li>
    </ul>
  </div>
</template>

<style>
/* 간단한 스타일링 */
#app { font-family: sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }
form { border: 1px solid #ccc; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
form div { margin-bottom: 10px; }
label { display: inline-block; width: 50px; }
input, textarea { width: 300px; padding: 5px; }
</style>