<template>
  <v-container>
  <h3 class="text-h5 font-weight-bold mb-6">관리자 페이지</h3>

    <v-row class="section-vertical-stack">
      <v-col cols="12"> <v-card class="pa-4 mb-4" outlined>
          <v-card-title class="text-h6 font-weight-semibold text-secondary">작가 등록 요청 관리</v-card-title>
          <v-card-text>
            <v-alert v-if="authors.length === 0" type="info" outlined class="mb-4">
              현재 보류 중인 작가가 없습니다.
            </v-alert>
            <div v-else class="d-flex overflow-x-auto py-2">
              <v-card
                v-for="author in authors"
                :key="author.id"
                @click="openPopup(author)"
                class="pa-3 mr-3 flex-shrink-0"
                width="150"
                height="100"
                outlined
                hover
              >
                <div class="text-caption text-center">👤 User ID: {{ author.userId }}</div>
                <div class="text-caption text-center">🧾 {{ author.profile }}</div>
              </v-card>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12"> <v-card class="pa-4 mb-4" outlined>
          <v-card-title class="text-h6 font-weight-semibold text-secondary">구독 모니터링</v-card-title>
          <v-card-text>
            <v-alert v-if="subscribeMonitors.length === 0" type="info" outlined class="mb-4">
              현재 구독 모니터링 데이터가 없습니다.
            </v-alert>
            <div v-else class="d-flex overflow-x-auto py-2">
              <v-card
                v-for="monitor in subscribeMonitors"
                :key="monitor.id"
                @click="openSubscribeMonitorPopup(monitor)"
                class="pa-3 mr-3 flex-shrink-0"
                width="150"
                height="100"
                outlined
                hover
              >
                <div class="text-caption text-center">📖 Book ID:{{ monitor.bookId }}</div>
                <div class="text-caption text-center">👤 User ID: {{ monitor.userId }}</div>
                <div class="text-caption text-center">✅ {{ monitor.state }}</div>
              </v-card>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="selectedAuthor" max-width="500px">
      <v-card>
        <v-card-title class="headline text-primary">작가 등록 요청 상세</v-card-title>
        <v-card-text v-if="selectedAuthor">
          <div class="mb-2"><strong>👤 User ID:</strong> {{ selectedAuthor.userId }}</div>
          <div class="mb-2"><strong>🧾 Profile:</strong> {{ selectedAuthor.profile }}</div>
          <div class="mb-2"><strong>📁 Portfolio:</strong> {{ selectedAuthor.portfolio }}</div>
        </v-card-text>
        <v-card-actions class="d-flex justify-center pt-4">
          <v-btn color="success" @click="approve(selectedAuthor.id)">✅ 승인</v-btn>
          <v-btn color="error" @click="deny(selectedAuthor.id)">❌ 거절</v-btn>
          <v-btn text @click="closePopup">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="selectedSubscribeMonitor" max-width="500px">
      <v-card>
        <v-card-title class="headline text-primary">구독 모니터링 상세</v-card-title>
        <v-card-text v-if="selectedSubscribeMonitor">
          <div class="mb-2"><strong>📖 도서 ID:</strong> {{ selectedSubscribeMonitor.bookId}}</div>
          <div class="mb-2"><strong>👤 사용자 ID:</strong> {{ selectedSubscribeMonitor.userId }}</div>
          <div class="mb-2"><strong>✅ 상태:</strong> {{ selectedSubscribeMonitor.state }}</div>
          <div class="mb-2"><strong>📅 만료일:</strong> {{ selectedSubscribeMonitor.expiredAt ? new Date(selectedSubscribeMonitor.expiredAt).toLocaleDateString() : '정보 없음' }}</div>
        </v-card-text>
        <v-card-actions class="d-flex justify-end pt-4">
          <v-btn text @click="closeSubscribeMonitorPopup">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="showConfirmationPopup" max-width="400px">
      <v-card>
        <v-card-title class="headline text-primary">알림</v-card-title>
        <v-card-text class="text-center text-secondary">{{ confirmationMessage }}</v-card-text>
        <v-card-actions class="d-flex justify-center pt-4">
          <v-btn color="primary" @click="closeConfirmationPopup">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/plugins/axios'

const authors = ref([])
const selectedAuthor = ref(null)
const confirmationMessage = ref('')
const showConfirmationPopup = ref(false)
const subscribeMonitors = ref([])
const selectedSubscribeMonitor = ref(null)

const fetchPendingAuthors = async () => {
  try {
    const res = await api.get('/authors/pending')
    // API 응답 구조에 따라 데이터 추출: _embedded.authors 또는 직접 데이터
    authors.value = res.data._embedded?.authors || res.data || []
  } catch (e) {
    console.error('작가 목록 조회 실패:', e) // 오류 발생 시 콘솔에 출력
  }
}

const openPopup = (author) => {
  selectedAuthor.value = author
}

const closePopup = () => {
  selectedAuthor.value = null
}

const closeConfirmationPopup = () => {
  showConfirmationPopup.value = false;
  confirmationMessage.value = '';
}

const approve = async (id) => {
  try {
    await axios.put(`/authors/${id}/authorapprove`)
    await fetchPendingAuthors()
    closePopup()
    confirmationMessage.value = '승인되었습니다.';
    showConfirmationPopup.value = true;
  } catch (e) {
    console.error('승인 실패:', e)
    confirmationMessage.value = '승인에 실패했습니다.';
    showConfirmationPopup.value = true;
  }
}

const deny = async (id) => {
  try {
    await axios.put(`/authors/${id}/authordeny`)
    await fetchPendingAuthors()
    closePopup()
    confirmationMessage.value = '거절되었습니다.';
    showConfirmationPopup.value = true;
  } catch (e) {
    console.error('거절 실패:', e)
    confirmationMessage.value = '거절에 실패했습니다.';
    showConfirmationPopup.value = true;
  }
}

onMounted(fetchPendingAuthors)

const fetchSubscribeMonitors = async () => {
  try {
    const res = await axios.get('/subscribeMonitors');
    subscribeMonitors.value = res.data._embedded?.subscribeMonitors || res.data || [];
    console.log('구독 모니터링 데이터 불러오기 성공:', subscribeMonitors.value);
  } catch (e) {
    console.error('구독 모니터링 목록 조회 실패:', e);
  }
};

const openSubscribeMonitorPopup = (monitor) => {
  selectedSubscribeMonitor.value = monitor;
};

const closeSubscribeMonitorPopup = () => {
  selectedSubscribeMonitor.value = null;
};

onMounted(() => {
  fetchPendingAuthors();
  fetchSubscribeMonitors();
});
</script>

<style scoped>
</style>