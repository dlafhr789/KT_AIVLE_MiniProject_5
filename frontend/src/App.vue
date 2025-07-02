<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

import Default from '@/layouts/default.vue'
import Home from '@/layouts/home.vue'

const route = useRoute()
const layout = computed(() =>
  route.meta.layout === 'home' ? Home : Default
)
</script>

<template>
  <VApp>
    <component :is="layout" />    <!-- 동적으로 레이아웃 결정 -->
  </VApp>
</template>