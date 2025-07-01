<template>
  <v-container>
    <h3>마이페이지</h3>

    <!-- 아이디 -->
    <v-text-field
      v-model="form.userId"
      label="아이디"
      outlined
      dense
      class="my-4"
    />

    <!-- 이름 -->
    <v-text-field
      v-model="form.userName"
      label="이름"
      outlined
      dense
      class="my-4"
    />

    <!-- 포인트 -->
    <v-text-field
      v-model="form.point"
      label="보유 포인트"
      type="number"
      outlined
      dense
      class="my-4"
    />

    <!-- 요금제 여부 -->
    <v-switch
      v-model="form.isSubscribed"
      label="구독중인가요?"
      class="my-4"
      inset
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
      label="자기소개서"
      outlined
      rows="3"
      class="my-4"
    />

    <!-- 요금제 버튼 -->
    <v-row class="mt-6" justify="center">
      <v-btn
        color="success"
        v-if="!form.isSubscribed"
        @click="subscribe"
      >
        <v-icon left small>mdi-check-circle</v-icon>
        요금제 가입하기
      </v-btn>
      <v-btn
        color="error"
        v-else
        @click="unsubscribe"
      >
        <v-icon left small>mdi-cancel</v-icon>
        요금제 해지하기
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'AuthorFormSimple',
  data() {
    return {
      form: {
        userId: '',
        userName: '',
        point: 0,
        isSubscribed: false,
        portfolio: '',
        profile: '',
      },
    };
  },
  methods: {
    registerAuthor() {
      console.log('등록:', this.form);
    },
    updateAuthor() {
      console.log('수정:', this.form);
    },
    subscribe() {
      this.form.isSubscribed = true;
      console.log('요금제 가입 처리');
    },
    unsubscribe() {
      this.form.isSubscribed = false;
      console.log('요금제 해지 처리');
    },
  },
};
</script>
