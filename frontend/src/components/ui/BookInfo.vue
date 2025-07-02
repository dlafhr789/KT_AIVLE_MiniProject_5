<template>
  <!-- 전체 페이지에서 가로·세로 중앙 정렬 -->
  <div
    class="book-detail-wrapper"
    style="display: flex; justify-content: center; align-items: center; min-height: 100vh;"
  >
    <div
      class="book-detail-container"
      style="display: flex; padding: 2rem; gap: 2rem; background: #fff; border-radius: 8px;"
    >
      <!-- 왼쪽: 책 표지 -->
      <div class="book-cover" style="flex: 0 0 300px;">
        <img
          :src="book.coverUrl"
          alt="책 표지"
          style="width: 100%; height: auto; object-fit: cover; border: 1px solid #ddd;"
        />
      </div>

      <!-- 오른쪽: 책 정보 + 버튼 -->
      <div
        class="book-info"
        style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;"
      >
        <!-- 책 메타 정보 -->
        <div>
          <h1 style="font-size: 1.8rem; margin-bottom: 0.5rem;">{{ book.title }}</h1>
          <p style="color: #555; margin-bottom: 1rem;">작가: {{ book.authorName }}</p>
          <p style="line-height: 1.6; margin-bottom: 1rem;">{{ book.summary }}</p>
          <p style="font-weight: bold;">포인트: {{ book.points }}</p>
        </div>

        <!-- 액션 버튼 -->
        <div class="actions" style="display: flex; gap: 1rem;">
          <button
            v-if="!book.isSubscribed"
            @click="subscribe"
            style="padding: 0.75rem 1.5rem; background:#3b82f6; color:#fff; border:none; border-radius:4px;"
          >
            구독하기 (소장/대여)
          </button>
          <button
            v-else
            @click="readBook"
            style="padding: 0.75rem 1.5rem; background:#10b981; color:#fff; border:none; border-radius:4px;"
          >
            책 읽기
          </button>
          <button
            @click="goBack"
            style="padding: 0.75rem 1.5rem; background:#6b7280; color:#fff; border:none; border-radius:4px;"
          >
            책 목록 보기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';   // ← 이걸 꼭 추가!
// import BookGrid from './BookGrid.vue';

export default {
  name: 'BookDetail',
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      book: {
        coverUrl: '',    // 책 표지 URL
        title: '',       // 제목
        authorId: '',
        authorName: '',      // 작가명
        summary: '',     // 줄거리 요약
        points: 0,       // 포인트
        isSubscribed: false
      },
    }
  },
  created() {
    
    axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-rcoxip60nbj.ws-us120.gitpod.io'

    axios.put(`/books/${this.id}/openbook`)
        .then(res => {
            const data = res.data
            this.book.title = data.title
            this.book.authorId = data.userId
        }).catch(err => console.error(err))


    axios.get(`/genData/${this.id}`)
        .then(res => {
            const data = res.data
            console.log(data.coverURL)
            this.book.coverUrl = `https://8084-dlafhr789-ktaivleminipr-rcoxip60nbj.ws-us120.gitpod.io/${data.coverUrl}`
            console.log(this.book.coverUrl)
            this.book.summary = data.summary
            this.book.points = data.point
        })
    
    axios.get(`/users/${this.id}`)
        .then(res => {
          const data = res.data
          console.log('작가 정보 : ', data)
          this.book.authorName = data.name
        })


    const user = JSON.parse(localStorage.getItem('user'))
    console.log("유저 아이디 : ", user.id)

    axios.get('/subscribes',)
      .then(res => {
        const all = res.data._embedded.subscribes
        
          const filtered = all.filter(item => Number(item.userId) === Number(user.id) && Number(item.bookId) === Number(this.id))
          console.log(filtered.length)

          this.book.isSubscribed = filtered.length > 0 ? true : false
      }).catch(err => {
        console.error('리스트 불러오기 실패: ', err)
      })
  },
  methods: {
    subscribe() {
      // TODO: 구독 요청 처리
      const user = JSON.parse(localStorage.getItem('user'))
      axios.post(`/subscribes/ownbook/${this.id}`, {}, {
        headers: { 'userId': user.id }
      })
        .then(res => {
          alert('구독 성공')
          window.location.reload()
        }).catch(err => {
          console.error('구독 요청 실패 : ', err)
          alert('구독 실패')
        })
    },
    readBook() {
      // TODO: 읽기 페이지로 이동, e.g. this.$router.push(`/books/${this.id}/read`)
    },
    goBack() {
      // 책 목록으로 돌아가기
      this.$router.push('/books')
    }
  }
}
</script>

<style scoped>
/* 필요에 따라 CSS 클래스/스타일을 여기에 추가하세요 */
</style>