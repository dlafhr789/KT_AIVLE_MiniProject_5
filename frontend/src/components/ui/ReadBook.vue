<template>
  <div
    class="book-read-wrapper"
    style="display: flex; flex-direction: column; align-items: center; padding: 2rem;"
  >
    <!-- 헤더: 제목·저자 + 뒤로가기 -->
    <div
      class="header"
      style="width: 100%; max-width: 800px; display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;"
    >
      <div>
        <h1 style="font-size: 1.8rem; margin: 0;">{{ book.title }}</h1>
      </div>
      <button
        @click="goBack"
        style="padding: 0.5rem 1rem; background: #6b7280; color: #fff; border: none; border-radius: 4px;"
      >
        ← 뒤로가기
      </button>
    </div>

    <!-- 본문: 단순 텍스트 -->
    <div
      class="content-container"
      style="width: 100%; max-width: 800px; line-height: 1.6; overflow-y: auto; white-space: pre-wrap; height: 80vh; padding: 1rem; border: 1px solid #ddd; border-radius: 4px; background: #fafafa;"
    >
      {{ book.content }}
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BookRead',
  props: {
    bookId: {
      type: [String, Number],
      required: true,
    },
  },
  data() {
    return {
      book: {
        title: '',
        author: '',
        content: '',   // 단순 텍스트
      },
    };
  },
  created() {
    axios
      .get(`/books/${this.bookId}/read`)
      .then(res => {
        const data = res.data;
        this.book.title = data.title;
        this.book.userId = data.userId;
        this.book.content = data.content; // 단순 텍스트
      })
      .catch(err => {
        console.error('책 읽기 데이터 로드 실패:', err);
      });
  },
  methods: {
    goBack() {
      this.$router.push(`/books/${this.bookId}`);
    },
  },
};
</script>

<style scoped>
/* 필요시 추가 스타일링 */
</style>
