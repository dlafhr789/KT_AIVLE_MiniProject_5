<template>
  <VBadge v-bind="avatarBadgeProps">
    <VAvatar
      style="cursor: pointer;"
      color="primary"
      variant="tonal"
    >
      <VIcon icon="mdi-account" />

      <!-- SECTION Menu -->
      <VMenu
        activator="parent"
        width="230"
        location="bottom end"
        offset="14px"
      >
        <VList>
          <!-- 👉 User Avatar & Name -->
          <VListItem>
            <template #prepend>
              <VListItemAction start>
                <VBadge v-bind="avatarBadgeProps">
                  <VAvatar
                    color="primary"
                    size="40"
                    variant="tonal"
                  >
                    <VIcon icon="mdi-account" />
                  </VAvatar>
                </VBadge>
              </VListItemAction>
            </template>

            <VListItemTitle class="font-weight-semibold">
              {{ userName }}
            </VListItemTitle>
          </VListItem>

          <VDivider class="my-2" />

          <!-- 🟦 마이페이지 추가 -->
          <VListItem to="/mypage" link>
            <template #prepend>
              <VIcon class="me-2" icon="mdi-account-circle-outline" size="22" />
            </template>
            <VListItemTitle>마이페이지</VListItemTitle>
          </VListItem>

          <!-- 🔸 Logout -->
          <VListItem to="/">
            <template #prepend>
              <VIcon class="me-2" icon="mdi-logout-variant" size="22" />
            </template>
            <VListItemTitle>Logout</VListItemTitle>
          </VListItem>
        </VList>
      </VMenu>
      <!-- !SECTION -->
    </VAvatar>
  </VBadge>
</template>
