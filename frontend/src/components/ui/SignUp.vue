<!-- src/components/pages/SignUpPage.vue -->
<template>
  <v-container class="fill-height d-flex justify-center align-center">
    <v-card width="420" elevation="2">
      <v-card-title class="text-h6 py-4">회원가입</v-card-title>

      <v-form ref="form" @submit.prevent="onSubmit">
        <v-card-text class="pt-0">

          <v-text-field
            v-model="payload.name"
            label="이름"
            class="mb-4"
          />

            <v-text-field
            v-model="payload.email"
            label="이메일"
            type="email"
            class="mb-4"
            required
          />

          <v-row>
            <v-col cols="12" md="6" class="pb-0">
              <v-text-field
                v-model="payload.password"
                label="비밀번호"
                type="password"
                required
              />
            </v-col>
            <v-col cols="12" md="6" class="pb-0">
              <v-text-field
                v-model="payload.password2"
                label="비밀번호 확인"
                type="password"
                :error="passwordMismatch"
                :error-messages="passwordMismatch ? '비밀번호가 다릅니다' : ''"
                required
              />
            </v-col>
          </v-row>

          <v-checkbox
            v-model="payload.kt"
            density="comfortable"
            label="KT 고객이신가요?"
            class="mt-4"
          />
        </v-card-text>

        <v-card-actions class="px-4 pb-4">
          <v-btn
            type="submit"
            color="primary"
            block
            :disabled="!canSubmit"
          >
            회원가입
          </v-btn>
        </v-card-actions>
      </v-form>

    </v-card>
  </v-container>
</template>

<script setup>
import { reactive, computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const form   = ref(null)

/* ───────── 폼 데이터 ───────── */
const payload = reactive({
  email: '',
  name: '',
  password: '',
  password2: '',
  kt: false,
})

/* ───────── 유효성 ───────── */
const passwordMismatch = computed(
  () => payload.password && payload.password2 && payload.password !== payload.password2
)
const canSubmit = computed(() =>
  payload.email &&
  payload.password &&
  !passwordMismatch.value
)

/* ───────── 제출 ───────── */
import axios from 'axios'
axios.defaults.baseURL = 'https://literate-sniffle-vr4947x5g54cw7rp-8088.app.github.dev/'
async function onSubmit () {
  if (!canSubmit.value) return

  // 1. 회원가입 데이터 준비
  const signupPayload = {
    password: payload.password,
    email: payload.email,
    name: payload.name,
    telecom: payload.kt ? 'kt' : undefined
  }

  try {
    // 2. 회원가입 요청
    await axios.post('/users/signup', signupPayload)

    // 4. 가입 성공 시 라우팅
    router.push('/')
  } catch (e) {
    console.error('회원가입 실패:', e)
    alert('회원가입 중 오류가 발생했습니다')
  }
}
</script>

<style scoped>
.fill-height {
  min-height: 100vh;
}
</style>