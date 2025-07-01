<template>
  <section class="home-wrapper">
    <div class="hero">
      <div class="hero-content">
        <h1 style="color: white;">
          함께 시작합시다<br />
          독서 습관 기르기
        </h1>

        <div class="btn-group">
          <v-btn color="primary" @click="openLogin">로그인</v-btn>
          <v-btn variant="outlined" @click="router.push('/books')">둘러보기</v-btn>
        </div>
      </div>
    </div>

    <!-- 간단한 모달 예시 -->
    <LoginDialog v-model="loginOpen" @login="handleLogin" />
  </section>
</template>

<style scoped>
.home-wrapper {
  width: 100%;
  min-height: 100vh;
  background: #f5f6fa;     /* 원하는 배경 색상 */
}

/* ───────── hero ───────── */
.hero {
  display: flex;
  justify-content: center; /* 수평 중앙 */
  align-items: center;     /* 수직 중앙 */
  height: calc(100vh); /* 헤더 높이만큼 뺀 전체 영역, 필요시 조정 */
  background: url('https://i.pinimg.com/736x/c0/ca/5e/c0ca5ed8d0a8bfcd1cd47e2628511f8a.jpg') center/cover no-repeat;
  text-align: center;
  padding: 0 16px;
}

.hero-content {
  max-width: 480px;
  color: #fff;
}

.hero-content h1 {
  font-size: 2.4rem;
  font-weight: 700;
  line-height: 1.35;
  margin-bottom: 24px;
}

/* 버튼 그룹 간격 */
.btn-group {
  display: flex;
  justify-content: center;
  flex-direction: row;
  gap: 8px;
}

</style>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import LoginDialog from '@/components/vo/LoginDialog.vue'

const router = useRouter()
const loginOpen = ref(false)

function openLogin() {
  loginOpen.value = true
}

</script>
