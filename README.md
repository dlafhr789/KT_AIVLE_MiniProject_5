<h1 align="center">📚 AI 기반 자동출간 및 구독 플랫폼 – “KT 걷다가서재”</h1>

<p align="center">
  작가가 글을 쓰면, AI가 자동으로 표지 이미지를 생성하고<br/>
  이를 전자책으로 출간하는 출간 자동화 플랫폼입니다.
</p>

<p align="center">
  Spring Boot와 Vue.js 기반으로, 각 도메인을 독립 서비스로 구성한 마이크로서비스 아키텍처(MSA)를 구현했습니다.<br/>
  컨테이너 기반으로 서비스를 패키징하고, Azure Kubernetes Service(AKS)에 배포해<br/>
  확장성과 유연성을 갖춘 클라우드 네이티브 애플리케이션으로 완성했습니다.
</p>

---

## 📌 프로젝트 개요

- **프로젝트명**: KT 걷다가서재
- **기간**: 2025.06.25 ~ 2025.07.04

---

## 👥 팀원 소개

| <img src="https://github.com/fndl5759.png" width="100"/> | <img src="https://github.com/cucumberbatc.png" width="100"/> | <img src="https://github.com/dlafhr789.png" width="100"/> | <img src="https://github.com/thlee17.png" width="100"/> | <img src="https://github.com/gayomiiiii.png" width="100"/> | <img src="https://github.com/Mnemosyne1234.png" width="100"/> |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **이재승**| **김보라**| **전임록**| **이태현**| **최가영**| **최미소**|
| [@fndl5759](https://github.com/fndl5759) | [@cucumberbatc](https://github.com/cucumberbatc) | [@dlafhr789](https://github.com/dlafhr789) | [@thlee17](https://github.com/thlee17) | [@gayomiiiii](https://github.com/gayomiiiii) | [@Mnemosyne1234](https://github.com/Mnemosyne1234) |


---

### 🛠 기술 스택

### 🔹 Frontend

![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)
![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=Vite&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge)


### 🔹 Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate)
![Lombok](https://img.shields.io/badge/Lombok-CA2131?style=for-the-badge)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Spring Cloud Stream](https://img.shields.io/badge/Spring%20Cloud%20Stream-6DB33F?style=for-the-badge)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-6DB33F?style=for-the-badge)
![H2](https://img.shields.io/badge/H2_DB-1A237E?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)


### 🔹 DevOps & Infra

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)
![MSAEZ](https://img.shields.io/badge/MSAEZ-DD0031?style=for-the-badge)




---

## 📁 프로젝트 구조
수정해야함 


---

## 🔍 주요 기능 구현 방식

### 📘 도서 등록 
- 작가가 도서 제목, 본문 내용을 입력해 도서를 등록합니다.
- Spring Boot 기반 Backend에서 도서 정보를 DB에 저장하고,  
  Kafka를 통해 출간 프로세스를 비동기로 연계합니다.

### 🤖 도서 출간
- 등록된 도서의 내용을 기반으로 AI가 표지 이미지를 자동 생성합니다.
- 이미지 생성 완료 후, 전자책으로 자동 출간되며,  
  출간 완료 상태는 Kafka를 통해 각 서비스에 전달됩니다.

### 📚 도서 열람
- 사용자(독자)가 등록된 전자책 목록을 조회하고,  
  도서 상세 내용을 열람할 수 있습니다.
- 프론트엔드(Vue.js)에서 Axios를 사용해 도서 서비스 API를 호출해 구현했습니다.

### 👤 작가 요청/승인
- 일반 사용자가 작가 등록 요청을 제출할 수 있습니다.
- 관리자(운영자)는 작가 승인/거절을 처리하며,  
  승인된 사용자는 도서 등록이 가능해집니다.

### 📊 플랜 구매/취소
- 사용자가 유료 플랜(출간 플랜)을 구매하거나 취소할 수 있습니다.
- 플랜 정보 및 결제 상태는 별도의 마이크로서비스에서 관리하며,  
  결제 상태 변경 시 Kafka 이벤트로 관련 서비스에 전달됩니다.

  

---




