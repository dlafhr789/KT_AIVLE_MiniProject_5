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
        <VerticalNavSectionTitle :item="{ heading: '요금제 및 포인트 메뉴' }" />
        <VerticalNavLink
            :item="{
                title: '구독 관리',
                to: '/subscribes',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서 포인트 조회',
                to: '/bookPoints',
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
