<!-- src/components/vo/LoginDialog.vue -->
<template>
  <!-- 다이얼로그 자체 -->
  <v-dialog v-model="localOpen" max-width="420">
    <v-card>
      <v-card-title class="text-h6">로그인</v-card-title>

      <v-card-text>
        <v-text-field
          v-model="form.id"
          label="이메일"
          density="comfortable"
          outlined
          hide-details
          autocomplete="username"
        />
        <v-text-field
          v-model="form.pw"
          label="비밀번호"
          type="password"
          density="comfortable"
          outlined
          hide-details
          autocomplete="current-password"
        />
      </v-card-text>

      <v-card-actions>
        <v-spacer />

        <v-btn text @click="close">취소</v-btn>
        <v-btn
          color="primary"
          :disabled="!canSubmit"
          @click="submit"
        >
          로그인
        </v-btn>
        <v-btn text color="secondary" @click="goSignup">회원가입</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
<<<<<<< HEAD
<<<<<<< HEAD
import { useAuth } from '@/components/useAuth'
=======
>>>>>>> e395a4b (feat: 홈, 로그인, 회원가입)
=======
import { useAuth } from '@/components/useAuth'
>>>>>>> 88eb146 (default layout, drawcontent 역할따라 수정)
import axios from 'axios'      

/* -------- props / emits -------- */
const props = defineProps({ modelValue: { type: Boolean, default: false } })
const emit  = defineEmits(['update:modelValue', 'login'])
<<<<<<< HEAD
<<<<<<< HEAD
const { setUser } = useAuth() 
=======

>>>>>>> e395a4b (feat: 홈, 로그인, 회원가입)
=======
const { setUser } = useAuth() 
>>>>>>> 88eb146 (default layout, drawcontent 역할따라 수정)
/* -------- 다이얼로그 열림 상태 (local copy) -------- */
const router = useRouter()
const localOpen = ref(props.modelValue)
watch(() => props.modelValue, v => { localOpen.value = v })
watch(localOpen, v => emit('update:modelValue', v))

/* -------- 폼 상태 -------- */
const form = reactive({ id: '', pw: '' })
const canSubmit = computed(() => form.id && form.pw)
const loading     = ref(false)   
/* -------- 메소드 -------- */
function close()  { localOpen.value = false }
<<<<<<< HEAD
<<<<<<< HEAD
axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-1sra693swsb.ws-us120.gitpod.io'
=======
axios.defaults.baseURL = 'https://literate-sniffle-vr4947x5g54cw7rp-8088.app.github.dev'
>>>>>>> e395a4b (feat: 홈, 로그인, 회원가입)
=======
axios.defaults.baseURL = 'https://8088-dlafhr789-ktaivleminipr-1sra693swsb.ws-us120.gitpod.io'
>>>>>>> 88eb146 (default layout, drawcontent 역할따라 수정)
async function submit() {
  if (!canSubmit.value || loading.value) return
  loading.value = true
  try {
    // ① 로그인 요청
    const res = await axios.post('/users/login', {
      email: form.id,
      password: form.pw,
    })
    const user = res.data  // { id, name, telecom, ... }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 71e2a14 (마이페이지 수정 및 요금제 연동)
    // ✅ 여기 추가!
    sessionStorage.setItem("userId", user.id);

    // ② 유저 정보 저장 (예: localStorage)
    //localStorage.setItem('user', JSON.stringify(user))
    setUser(user)
=======
    // ② 유저 정보 저장 (예: localStorage)
<<<<<<< HEAD
    localStorage.setItem('user', JSON.stringify(user))
>>>>>>> e395a4b (feat: 홈, 로그인, 회원가입)
=======
    //localStorage.setItem('user', JSON.stringify(user))
    setUser(user)
>>>>>>> 88eb146 (default layout, drawcontent 역할따라 수정)

    // ③ 상위 컴포넌트로 성공 알림(선택)
    emit('login-success', user)

    // ④ 페이지 이동(필요 시)
    router.push('/books')

    close()
  } catch (e) {
    alert('아이디 또는 비밀번호가 올바르지 않습니다')
    console.error(e)
  } finally {
    loading.value = false
  }
}

function goSignup() {
  close()                 // 다이얼로그 먼저 닫고
  router.push('/signup')  // /signup으로 이동
}
</script>

<style scoped>
/* 필요 시 추가 커스터마이징 */
</style>
