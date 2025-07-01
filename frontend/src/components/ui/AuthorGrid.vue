<template>
  <v-container>
    <h3>작가 정보 입력하기</h3>

    <!-- 이메일 주소 -->
    <v-text-field
      v-model="form.userId"
      label="이메일 주소"
      outlined
      dense
      class="my-4"
    />

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
    };
  },
  methods: {
    registerAuthor() {
      console.log('등록:', this.form);
      // 실제 등록 API 호출
      this.snackbar.text = '등록되었습니다.';
      this.snackbar.show = true;
    },
    updateAuthor() {
      console.log('수정:', this.form);
      // 실제 수정 API 호출
      this.snackbar.text = '수정되었습니다.';
      this.snackbar.show = true;
    },
  },
};
</script>
