<template>
  <v-container>
    <h3 class="text-h5 font-weight-bold mb-6">마이페이지</h3>

    <!-- 아이디 -->
    <v-text-field
      v-model="form.userId"
      label="아이디"
      outlined
      dense
      class="my-4"
      readonly
    />

    <!-- 이름 -->
    <v-text-field
      v-model="form.userName"
      label="이름"
      outlined
      dense
      class="my-4"
      readonly
    />

    <!-- 포인트 -->
    <v-text-field
      v-model="form.point"
      label="보유 포인트"
      type="number"
      outlined
      dense
      class="my-4"
      readonly
    />

    <!-- 요금제 상태 -->
    <v-alert
      type="info"
      class="my-4"
      border="left"
      colored-border
    >
      {{ form.isSubscribed ? '현재 요금제를 구독 중입니다.' : '요금제를 구독하지 않았습니다.' }}
    </v-alert>

    <!-- 버튼 -->
    <v-row class="mt-6" justify="center">
      <v-btn
        color="primary"
        v-if="!form.isSubscribed"
        @click="subscribeDialog = true"
      >
        요금제 가입하기
      </v-btn>
      <v-btn
        color="error"
        v-else
        @click="unsubscribeDialog = true"
      >
        요금제 해지하기
      </v-btn>
    </v-row>

    <!-- 가입 팝업 -->
    <v-dialog v-model="subscribeDialog" max-width="500px">
      <v-card>
        <v-card-title>요금제 가입</v-card-title>
        <v-card-text>요금제를 가입하시겠습니까?</v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="subscribeDialog = false">아니요</v-btn>
          <v-btn color="primary" @click="handleSubscribe">예</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 해지 팝업 -->
    <v-dialog v-model="unsubscribeDialog" max-width="500px">
      <v-card>
        <v-card-title>요금제 해지</v-card-title>
        <v-card-text>정말로 해지하시겠습니까?</v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="unsubscribeDialog = false">아니요</v-btn>
          <v-btn color="error" @click="handleUnsubscribe">예</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from 'axios'

// ✅ Gateway 주소로 설정 (반드시 8088 포트의 Gitpod public 주소)
axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-mcnl0299kxi.ws-us120.gitpod.io'

export default {
  name: 'MyPage',
  data() {
    return {
      form: {
        dbId: null,
        userId: '',
        userName: '',
        point: 0,
        isSubscribed: false,
        plan: null,
      },
      subscribeDialog: false,
      unsubscribeDialog: false,
    };
  },
  created() {
    this.fetchUser();
  },
  methods: {
    async fetchUser() {
      try {
        const res = await axios.get('/users');
        const users = res.data._embedded?.users || [];

        if (users.length > 0) {
          const user = users[0]; // 로그인 사용자 1명 가정

          this.form.dbId = user.id;
          this.form.userId = user.email;
          this.form.userName = user.name;
          this.form.point = user.point;
          this.form.plan = user.plan;
          this.form.isSubscribed = !!user.plan;
        }
      } catch (err) {
        console.error('유저 정보 조회 실패:', err);
        alert('유저 정보를 불러오는 중 오류가 발생했습니다.');
      }
    },

    async handleSubscribe() {
      try {
        // ✅ POST → PUT, 경로 소문자
        await axios.put(`/users/${this.form.dbId}/planpurchase`, {
          plan: 'basic'
        });

        this.form.isSubscribed = true;
        this.subscribeDialog = false;
        alert('요금제 가입 완료');
      } catch (err) {
        console.error('요금제 가입 실패:', err);
        alert('가입 중 오류가 발생했습니다');
      }
    },

    async handleUnsubscribe() {
      try {
        console.log('해지 버튼 클릭 시 form:', this.form);
        console.log('해지 요청 ID:', this.form?.dbId);
        // 해지 경로도 PUT 방식
        await axios.put(`/users/${this.form.dbId}/plancancel`, {});

        this.form.isSubscribed = false;
        this.form.plan = null;
        this.unsubscribeDialog = false;
        alert('요금제 해지 완료');
      } catch (err) {
        console.error('요금제 해지 실패:', err);
        alert('해지 중 오류가 발생했습니다');
      }
    },
  },
};
</script>

<style scoped>
</style>
