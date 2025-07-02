<template>
  <div>
    <h1>도서 목록</h1>
    <ul>
      <li v-for="book in books" :key="book.id">
        <strong>{{ book.title }}</strong> (조회수: {{ book.view }})
      </li>
    </ul>
    <p v-if="books.length === 0">표시할 책이 없습니다.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BookListView',
  
  // 1. 컴포넌트의 데이터(상태)를 정의하는 곳입니다.
  data() {
    return {
      books: [], // 책 목록을 담을 빈 배열
    };
  },
  
  // 2. 컴포넌트에서 사용할 함수(메소드)들을 정의하는 곳입니다.
  methods: {
    async fetchBooks() {
      try {
        const response = await axios.get('/books');
        if (response.data && response.data._embedded && response.data._embedded.books) {
          // data에 접근할 때는 'this.books'를 사용합니다.
          this.books = response.data._embedded.books;
        }
      } catch (error) {
        console.error("책 목록을 불러오는 데 실패했습니다:", error);
      }
    },
  },
  
  // 3. 컴포넌트가 화면에 그려진 직후에 실행되는 부분입니다.
  mounted() {
    // 메소드를 호출할 때도 'this.fetchBooks()'를 사용합니다.
    this.fetchBooks();
  }
};
</script>

<style scoped>
  ul { list-style: none; padding: 0; }
  li { background-color: #f4f4f4; margin-bottom: 10px; padding: 15px; border-radius: 5px; }
</style>