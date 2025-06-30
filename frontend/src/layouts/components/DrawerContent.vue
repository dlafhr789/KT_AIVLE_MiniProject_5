<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '도서 관리' }" />
        <VerticalNavLink
            :item="{
                title: 'Book',
                to: '/books',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '유저 관리' }" />
        <VerticalNavLink
            :item="{
                title: 'User',
                to: '/users',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'Subscribe',
                to: '/subscribes',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서 포인트 조회',
                to: '/bookPoints',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '도서 출간 서비스 관리' }" />
        <VerticalNavLink
            :item="{
                title: 'GenData',
                to: '/genData',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '작가 관리' }" />
        <VerticalNavLink
            :item="{
                title: 'Author',
                to: '/authors',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '대쉬보드' }" />
        <VerticalNavLink
            :item="{
                title: '구독 모니터링',
                to: '/subscribeMonitors',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서목록',
                to: '/booklists',
            }"
        />
    </ul>
</template>
