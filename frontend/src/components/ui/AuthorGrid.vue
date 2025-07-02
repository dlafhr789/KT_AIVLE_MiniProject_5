<template>
  <v-container>
    <h3 class="text-h5 font-weight-bold mb-6">작가 정보 입력하기</h3>

    <!-- 포트폴리오 주소 -->
    <v-text-field
      v-model="form.portfolio"
      label="포트폴리오 주소"
      outlined
      dense
      class="my-4"
    />

    <!-- 자소서 -->
    <v-textarea
      v-model="form.profile"
      label="자소서"
      outlined
      rows="3"
      class="my-4"
    />

    <!-- 버튼 -->
    <v-row class="mt-4" justify="space-between">
      <v-col cols="5">
        <v-btn block color="primary" @click="registerAuthor">
          <v-icon left small>mdi-plus</v-icon>
          등록
        </v-btn>
      </v-col>
      <v-col cols="5">
        <v-btn block color="primary" @click="updateAuthor">
          <v-icon left small>mdi-pencil</v-icon>
          수정
        </v-btn>
      </v-col>
    </v-row>

    <!-- ✅ Snackbar -->
    <v-snackbar v-model="snackbar.show" :timeout="2000" color="success">
      {{ snackbar.text }}
    </v-snackbar>
  </v-container>
</template>

<script>
import axios from 'axios';
// axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-1sra693swsb.ws-us120.gitpod.io'
import { useAuth } from '@/components/useAuth'
import api from '@/plugins/axios'
export default {
  name: 'AuthorFormSimple',
  data() {
    return {
      form: {
        userId: '',
        portfolio: '',
        profile: '',
      },
      snackbar: {
        show: false,
        text: '',
      },
      authorId: null, // 저장용 ID 필드 추가
    }
  },
  setup() {
    const { state } = useAuth()     // 로그인 사용자 접근
    return { state }
  },
  methods: {
    async registerAuthor() {
      try {
        // POST
        const res = await api.post('/authors-requests',
          {
            portfolio: this.form.portfolio,
            profile: this.form.profile,
          },
          {
            headers: {
              'X-User-Id': this.state.user.id,
            },
          }
        )

        // ② authorId 저장 (필요 시)
        this.authorId = res.data.id

        // ② 알림
        this.snackbar.text = '등록되었습니다.';
        this.snackbar.show = true;

      } catch (err) {
        console.error(err);
        this.$toast?.error('등록에 실패했습니다');
      }
    },
    async updateAuthor() {
      try {
        const authorId = this.authorId
        await api.put(`/authors/${authorId}`, {
          portfolio: this.form.portfolio,
          profile:   this.form.profile,
        },
        {
          headers: {
          'X-User-Id': this.state.user.id,
          },
        }
      );

        this.snackbar.text = '수정되었습니다.';
        this.snackbar.show = true;
      } catch (err) {
        console.error(err);
        this.$toast?.error('수정에 실패했습니다');
      }
    },
  },
};
</script>
