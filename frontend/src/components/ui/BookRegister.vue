<!-- src/components/ui/BookRegister.vue -->
<template>
  <v-container>
    <!-- 제목 -->
    <h3 class="text-h5 font-weight-bold mb-6">새 책 등록 & 도서 출간 요청</h3>
      <v-row>
        <!-- 제목 -->
        <v-col cols="12">
          <v-text-field
            v-model="form.title"
            label="제목"
            outlined
            dense
            :rules="[v => !!v || '필수 입력 항목입니다.']"
            required
          />
        </v-col>

        <!-- 내용 -->
        <v-col cols="12">
          <v-textarea
            v-model="form.content"
            label="내용"
            auto-grow
            outlined
            rows="3"
            dense
          />
        </v-col>
      </v-row>

      <!-- 버튼 -->
      <v-row class="mt-6" justify="end" dense>
        <v-btn color="grey" variant="tonal" class="me-3" @click="resetForm">
          취소
        </v-btn>
        <v-btn color="primary" variant="tonal" class="me-3" @click="handleSave">
          저장하기
        </v-btn>
        <!-- <v-btn color="primary" @click="handlePublish">
          출간요청하기
        </v-btn> -->
      </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'
import { useAuth } from '@/components/useAuth'
import api from '@/plugins/axios'
// Gateway 주소
// axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-mcnl0299kxi.ws-us120.gitpod.io'

export default {
  name: 'BookRegister',

  data() {
    return {
      form: {
        title: '',
        content: '',
        bookId: null,
      },
    }
  },
    setup() {
    const { state } = useAuth()     // 로그인 사용자 접근
    return { state }
  },
  methods: {
    validate() {
      if (!this.form.title) {
        alert('제목은 필수입니다!')
        return false
      }
      return true
    },

    resetForm() {
      // 입력 초기화
      this.form = {
        title: '',
        content: '',
      }
    },

    // 저장하기 POST
    async handleSave() {
      if (!this.validate()) return

      try {
        // this.isSubmitting = true
        await api.post('/books',
          {
            title: this.form.title,
            content: this.form.content,
          },
          {
            headers: {
              'X-User-Id': this.state.user.id,
            }
          }
        )
        alert('책이 저장되었습니다!')
        this.$router.push('/books')
      } catch (err) {
        console.error(err)
        alert('저장 중 오류가 발생했습니다.')
      } finally {
        this.isSubmitting = false
      }
    },

    // 출간요청하기
    async handlePublish() {
      if (!this.validate()) return

      try {
        // this.isSubmitting = true
        let bookId = this.form.bookId 
        // 1) bookId 가 없으면 먼저 저장
        if (!bookId) {
          const { data } = await api.post('/books', {
            title: this.form.title,
            content: this.form.content,
          }, {
            headers: { 'X-User-Id': this.state.user.id },
          })
        bookId = data.bookId ?? data.id
        this.form.bookId = bookId   
        }

        // 2) AI 도메인에 출간 요청
        await api.post(`/books/${bookId}`, {
          title: this.form.title,
          content: this.form.content,
        },
        {headers: { 'X-User-Id': this.state.user.id } }
      )

        alert('출간이 요청되었습니다! (AI 생성 중)')
        this.$router.push('/books')
      } catch (err) {
        console.error(err)
        alert('출간 요청 중 오류가 발생했습니다.')
      } finally {
        this.isSubmitting = false
      }
    },  

  },
}
</script>