<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: 'Book' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Subscribe' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'User' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Manage' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Plan' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Ai출간서비스' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
    </ul>
</template>
