<template>
  <div class="admin-dashboard">
    <button @click="goBackToBooks" class="back-to-books-button">
      Back
    </button>

    <div class="dashboard-title-wrapper">
      <h2 class="dashboard-title">관리자 대시보드</h2>
    </div>

    <div class="section-vertical-stack">
      <section class="author-request-section">
        <h3 class="section-title">작가 등록 요청 관리</h3>
        <div v-if="authors.length === 0" class="no-authors-message">현재 보류 중인 작가가 없습니다.</div>
        <div v-else class="author-items-horizontal-scroll-container">
          <div
            v-for="author in authors"
            :key="author.id"
            @click="openPopup(author)"
            class="author-item"
          >
            <div class="author-id">👤 User ID: {{ author.userId }}</div>
            <div class="author-profile">🧾 {{ author.profile }}</div>
          </div>
        </div>
      </section>

      <section class="subscription-monitoring-section">
        <h3 class="section-title">구독 모니터링</h3>
        <div v-if="subscribeMonitors.length === 0" class="no-subscribers-message">
          현재 구독 모니터링 데이터가 없습니다.
        </div>
        <div v-else class="monitor-items-horizontal-scroll-container">
          <div
            v-for="monitor in subscribeMonitors"
            :key="monitor.id"
            @click="openSubscribeMonitorPopup(monitor)"
            class="monitor-item"
          >
            <div class="monitor-book-title">📖 Book ID:{{ monitor.bookId }}</div>
            <div class="monitor-user-name">👤 User ID: {{ monitor.userId }}</div>
            <div class="monitor-state">✅ {{ monitor.state }}</div>
          </div>
        </div>
      </section>
      </div>

    <div v-if="selectedAuthor" class="popup-overlay">
      <div class="popup-content">
        <button @click="closePopup" class="popup-close-button">×</button>
        <h3 class="popup-title">작가 등록 요청 상세</h3>
        <div class="popup-detail"><strong>👤 User ID:</strong> {{ selectedAuthor.userId }}</div>
        <div class="popup-detail"><strong>🧾 Profile:</strong> {{ selectedAuthor.profile }}</div>
        <div class="popup-detail"><strong>📁 Portfolio:</strong> {{ selectedAuthor.portfolio }}</div>
        <div class="popup-actions">
          <button @click="approve(selectedAuthor.id)" class="approve-button">✅ 승인</button>
          <button @click="deny(selectedAuthor.id)" class="deny-button">❌ 거절</button>
        </div>
      </div>
    </div>

    <div v-if="selectedSubscribeMonitor" class="popup-overlay">
      <div class="popup-content">
        <button @click="closeSubscribeMonitorPopup" class="popup-close-button">×</button>
        <h3 class="popup-title">구독 모니터링 상세</h3>

        <!--
        <div class="popup-detail"><strong>📖 도서 제목:</strong> {{ selectedSubscribeMonitor.bookTitle || '정보 없음' }}</div>
        <div class="popup-detail"><strong>👤 사용자 이름:</strong> {{ selectedSubscribeMonitor.userName }}</div>
        -->
        <div class="popup-detail"><strong>📖 도서 ID:</strong> {{ selectedSubscribeMonitor.bookId}}</div>
        <div class="popup-detail"><strong>👤 사용자 ID:</strong> {{ selectedSubscribeMonitor.userId }}</div>
        <div class="popup-detail"><strong>✅ 상태:</strong> {{ selectedSubscribeMonitor.state }}</div>
        <div class="popup-detail"><strong>📅 만료일:</strong> {{ selectedSubscribeMonitor.expiredAt ? new Date(selectedSubscribeMonitor.expiredAt).toLocaleDateString() : '정보 없음' }}</div>
        
        </div>
    </div>
    <div v-if="showConfirmationPopup" class="popup-overlay">
      <div class="popup-content confirmation-popup">
        <button @click="closeConfirmationPopup" class="popup-close-button">×</button>
        <h3 class="popup-title">알림</h3>
        <p class="confirmation-message">{{ confirmationMessage }}</p>
        <div class="popup-actions-center">
          <button @click="closeConfirmationPopup" class="confirm-button">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import api from '@/plugins/axios'
import { useRouter } from 'vue-router'

// 주소 확인 후 변경 필요 @@@@@@@@
// axios.defaults.baseURL = 'https://humble-rotary-phone-9x4vx569j7fx95v-8088.app.github.dev'

// 반응형 데이터 선언
const authors = ref([]) // 작가 목록을 저장할 배열
const selectedAuthor = ref(null) // 팝업에 표시될 선택된 작가 객체
const confirmationMessage = ref('') // 확인 팝업에 표시될 메시지
const showConfirmationPopup = ref(false) // 확인 팝업 표시 여부

// 보류 중인 작가 목록을 API에서 가져오는 함수
const fetchPendingAuthors = async () => {
  try {
    const res = await api.get('/authors/pending')
    // API 응답 구조에 따라 데이터 추출: _embedded.authors 또는 직접 데이터
    authors.value = res.data._embedded?.authors || res.data || []
  } catch (e) {
    console.error('작가 목록 조회 실패:', e) // 오류 발생 시 콘솔에 출력
  }
}

// 작가 클릭 시 팝업을 열고 선택된 작가 정보를 설정하는 함수
const openPopup = (author) => {
  selectedAuthor.value = author
}

// 작가 상세 팝업을 닫는 함수
const closePopup = () => {
  selectedAuthor.value = null
}

// 확인 팝업을 닫는 함수
const closeConfirmationPopup = () => {
  showConfirmationPopup.value = false;
  confirmationMessage.value = '';
}

// 작가 등록 요청을 승인하는 함수
const approve = async (id) => {
  try {
    await api.put(`/authors/${id}/authorapprove`) // 승인 API 호출
    await fetchPendingAuthors() // 목록 새로고침
    closePopup() // 상세 팝업 닫기
    confirmationMessage.value = '승인되었습니다.'; // 확인 메시지 설정
    showConfirmationPopup.value = true; // 확인 팝업 표시
  } catch (e) {
    console.error('승인 실패:', e) // 오류 발생 시 콘솔에 출력
    confirmationMessage.value = '승인에 실패했습니다.'; // 실패 메시지 설정
    showConfirmationPopup.value = true; // 확인 팝업 표시
  }
}

// 작가 등록 요청을 거절하는 함수
const deny = async (id) => {
  try {
    await api.put(`/authors/${id}/authordeny`) // 거절 API 호출
    await fetchPendingAuthors() // 목록 새로고침
    closePopup() // 상세 팝업 닫기
    confirmationMessage.value = '거절되었습니다.'; // 확인 메시지 설정
    showConfirmationPopup.value = true; // 확인 팝업 표시
  } catch (e) {
    console.error('거절 실패:', e) // 오류 발생 시 콘솔에 출력
    confirmationMessage.value = '거절에 실패했습니다.'; // 실패 메시지 설정
    showConfirmationPopup.value = true; // 확인 팝업 표시
  }
}

// 컴포넌트가 마운트될 때 작가 목록을 가져오도록 설정
onMounted(fetchPendingAuthors)


// @@@@@@@@@@@@@@@@@@@ 구독 모니터링 부분 @@@@@@@@@@@@@@@@@@@@@@

// --- 구독 모니터링 관련 데이터 추가 ---
const subscribeMonitors = ref([]) // 구독 모니터링 목록을 저장할 배열
const selectedSubscribeMonitor = ref(null) // 구독 모니터링 팝업에 표시될 선택된 객체

/**
 * 모든 구독 모니터링 정보를 API에서 가져오는 함수
 */
const fetchSubscribeMonitors = async () => {
  try {
    // Spring Data REST가 자동 생성한 모든 구독 모니터 조회 API 호출
    const res = await api.get('/subscribeMonitors');
    // Spring Data REST 응답 구조 (_embedded.subscribeMonitors)에 따라 데이터 추출
    subscribeMonitors.value = res.data._embedded?.subscribeMonitors || res.data || [];
    console.log('구독 모니터링 데이터 불러오기 성공:', subscribeMonitors.value);
  } catch (e) {
    console.error('구독 모니터링 목록 조회 실패:', e); // 오류 발생 시 콘솔에 출력
  }
};

/**
 * 특정 구독 모니터 항목 클릭 시 팝업을 열고 선택된 정보를 설정하는 함수
 */
const openSubscribeMonitorPopup = (monitor) => {
  selectedSubscribeMonitor.value = monitor;
};

/**
 * 구독 모니터 상세 팝업을 닫는 함수
 */
const closeSubscribeMonitorPopup = () => {
  selectedSubscribeMonitor.value = null;
};


// 컴포넌트가 마운트될 때 데이터들을 가져오도록 설정
onMounted(() => {
  fetchPendingAuthors(); // 기존 작가 목록 가져오기
  fetchSubscribeMonitors(); // 새로 추가된 구독 모니터링 목록 가져오기
});

// 새로운 뒤로가기 함수
const router = useRouter()

const goBackToBooks = () => {
  router.push('/books'); // '/books' 경로로 이동
};
</script>

<style scoped>

/* 전체 대시보드 컨테이너 스타일 */
.admin-dashboard {
  padding: 1.5rem;
  position: relative;
  min-height: 100vh;
  background-color: transparent; /* 컴포넌트 자체 배경 투명 */
  color: var(--color-text-secondary); /* 기본 텍스트 색상 적용 (어두운 배경용) */
}

/* 대시보드 제목을 감싸는 새로운 컨테이너 */
.dashboard-title-wrapper {
  width: 100%;
}

/* 대시보드 제목 스타일 */
.dashboard-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
  color: var(--color-text-primary); /* 메인 제목 색상 적용 */
}

/* 뒤로가기 버튼 스타일 */
.back-to-books-button {
  background-color: #9155FD; /* 보라색 */
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  border: none;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background-color 0.15s ease-in-out;

  position: absolute;
  top: 1.5rem;
  right: 1.5rem; 
  z-index: 10; 
}

.back-to-books-button:hover {
  background-color: #6A1EB8; /* 약간 더 진한 보라색 */
}

/* 섹션들을 상하로 배치하기 위한 플렉스 컨테이너 */
.section-vertical-stack {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 각 섹션(작가 요청, 구독 모니터링)의 공통 스타일 */
.author-request-section,
.subscription-monitoring-section {
  flex: 1;
  background-color: var(--color-section-bg); 
  border-radius: 0.5rem;
  padding: 1rem;
  border: 0.5px solid; 
  min-width: 300px;
}

/* 각 섹션 내부 제목 스타일 */
.section-title {
  font-size: 1.125rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: var(--color-text-secondary); /* 섹션 제목 색상 적용 */
}

/* 작가가 없거나 모니터링 영역의 플레이스홀더 메시지 스타일 */
.no-authors-message,
.monitoring-placeholder {
  color: var(--color-text-tertiary); /* 보조 텍스트 색상 적용 */
}

/* 작가 목록 가로 스크롤 컨테이너 */
.author-items-horizontal-scroll-container {
  display: flex;
  flex-wrap: nowrap;
  gap: 0.75rem;
  overflow-x: auto;
  padding-bottom: 0.5rem;
  overflow-y: hidden;
  max-height: 120px; /* 스크롤바가 생기면 컨텐츠 높이를 제한 */
}

/* 각 작가 아이템 스타일 (네모난 형태) */
.author-item {
  background-color: var(--color-item-bg); /* 아이템 배경색 적용 */
  border-radius: 0.5rem;
  padding: 0.75rem;
  cursor: pointer;
  transition: background-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  width: 150px;
  height: 100px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 1px solid; /* 테두리 색상 적용 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 아이템 그림자 유지 */
  color: var(--color-text-secondary); /* 아이템 텍스트 색상 적용 */
}

/* 작가 아이템에 마우스 오버 시 배경색 변경 */
.author-item:hover {
  background-color: var(--color-item-hover-bg); /* 아이템 호버 배경색 적용 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 아이템 호버 시 그림자 강화 */
}


/* 구독 모니터링 목록 가로 스크롤 컨테이너 - 작가 목록과 동일한 스타일 적용 */
.monitor-items-horizontal-scroll-container {
  display: flex;
  flex-wrap: nowrap;
  gap: 0.75rem; /* 작가 아이템과 동일한 간격 */
  overflow-x: auto;
  padding-bottom: 0.5rem;
  overflow-y: hidden;
  max-height: 120px; /* 스크롤바가 생기면 컨텐츠 높이를 제한 */
}

/* 각 구독 모니터 아이템 스타일 (작가 아이템과 유사하게 카드 형태) */
.monitor-item {
  background-color: var(--color-item-bg); /* 아이템 배경색 적용 */
  border-radius: 0.5rem;
  padding: 0.75rem;
  cursor: pointer;
  transition: background-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  width: 150px;
  height: 100px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border: 1px solid; /* 테두리 색상 적용 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 아이템 그림자 유지 */
  color: var(--color-text-secondary); /* 아이템 텍스트 색상 적용 */
}

/* 구독 모니터 아이템에 마우스 오버 시 배경색 변경 */
.monitor-item:hover {
  background-color: var(--color-item-hover-bg); /* 아이템 호버 배경색 적용 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 아이템 호버 시 그림자 강화 */
}
/* 팝업 오버레이 (화면 전체를 덮음) */
.popup-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.5); /* 반투명 검정 배경 고정 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 50;
}

/* 팝업 내용 컨테이너 */
.popup-content {
  background-color: rgb(159, 155, 160); /* 팝업 배경색 적용 */
  color: var(--color-text-secondary); /* 팝업 글자색 적용 */
  border-radius: 1rem;
  border: 1px solid; /* 테두리 색상 적용 */
  padding: 1.5rem;
  width: 90%;
  max-width: 28rem;
  position: relative;
}

/* 팝업 닫기 버튼 */
.popup-close-button {
  position: absolute;
  top: 0.75rem;
  right: 1rem;
  color: var(--color-popup-close-button); /* 팝업 닫기 버튼 색상 적용 */
  font-size: 2rem;
  font-weight: bold;
  background: none;
  border: none;
  cursor: pointer;
}

.popup-close-button:hover {
  color: var(--color-popup-close-button-hover); /* 팝업 닫기 버튼 호버 색상 적용 */
}

/* 팝업 제목 스타일 */
.popup-title {
  font-size: 1.125rem;
  font-weight: bold;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid var(--color-border-primary); /* 구분선 색상 적용 */
  color: var(--color-text-primary); /* 팝업 제목 색상 적용 */
}

/* 팝업 상세 정보 항목 스타일 */
.popup-detail {
  margin-bottom: 0.5rem;
  color: var(--color-text-secondary); /* 텍스트 색상 적용 */
}

.popup-detail strong {
  color: var(--color-text-primary); /* 볼드 텍스트 색상 적용 */
}

/* 팝업 내 버튼 그룹 (승인/거절) */
.popup-actions {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid var(--color-border-primary); /* 구분선 색상 적용 */
}

/* 승인/거절 버튼 공통 스타일 */
.approve-button,
.deny-button {
  color: white; /* 버튼 내부 글자는 항상 흰색 (버튼 배경색이 이미 대비를 제공) */
  padding: 0.5rem 1.5rem;
  border-radius: 0.375rem;
  border: 1px solid;
  cursor: pointer;
  transition: background-color 0.15s ease-in-out;
  text-transform: none;
}

/* 승인 버튼 고유 스타일 */
.approve-button {
  background-color: #9155FD;
  border-color: #9155FD;
}

.approve-button:hover {
  background-color: #6A1EB8;
}

/* 거절 버튼 고유 스타일 */
.deny-button {
  background-color: #9155FD;
  border-color: #9155FD;
}

.deny-button:hover {
  background-color: #6A1EB8;
}

/* 확인 팝업 메시지 스타일 */
.confirmation-popup .confirmation-message {
  text-align: center;
  font-size: 1.1rem;
  margin-bottom: 1.5rem;
  margin-top: 1rem;
  color: var(--color-text-secondary); /* 텍스트 색상 적용 */
}

/* 확인 팝업 버튼 중앙 정렬 */
.popup-actions-center {
  display: flex;
  justify-content: center;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid var(--color-border-primary); /* 구분선 색상 적용 */
}

.confirm-button {
  background-color: #007bff; /* Blue */
  color: white;
  padding: 0.5rem 1.5rem;
  border-radius: 0.375rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.15s ease-in-out;
  text-transform: none;
}

.confirm-button:hover {
  background-color: #0056b3;
}

/* 반응형을 위한 미디어 쿼리 */
@media (max-width: 768px) {
  .section-vertical-stack {
    gap: 1rem;
  }

  .author-request-section,
  .subscription-monitoring-section {
    min-width: unset;
    width: 100%;
  }

  .author-items-horizontal-scroll-container {
    justify-content: flex-start;
  }

  .author-item {
    width: 120px;
    height: 80px;
  }

  .popup-content {
    max-width: 95%;
  }
}
</style>