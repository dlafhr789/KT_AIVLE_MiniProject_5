<script setup>
import { reactive } from 'vue'
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
import { useAuth } from '@/components/useAuth'

//const auth = reactive(JSON.parse(localStorage.getItem('user') || '{}'))
const { state: auth } = useAuth()
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '책 메뉴' }" />
        <VerticalNavLink
            :item="{
                title: '도서 목록',
                to: '/books',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '개인 정보 관리 메뉴' }" />
        <VerticalNavLink
            :item="{
                title: '마이페이지',
                to: '/mypage',
            }"
        />
        <template v-if="auth.user?.role === 'author'">
        <VerticalNavSectionTitle :item="{ heading: '도서 출간 메뉴' }" />
        <VerticalNavLink
            :item="{
                title: '도서 출간 요청',
                to: '/authors',
            }"
        />
        </template>
        <template v-if="auth.user?.role === 'ADMIN'">
        <VerticalNavSectionTitle :item="{ heading: '관리자 메뉴' }" />
        <VerticalNavLink
            :item="{
                title: '작가 등록 관리',
                to: '/manage',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독 모니터링',
                to: '/subscribeMonitors',
            }"
        />
        </template>
    </ul>
</template>
