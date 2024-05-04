<script setup>
defineProps(["showModal"]);
</script>

<template>
  <div>
    <Teleport id="delete" to="#modal">
      <div
        v-if="showModal"
        class="absolute left-0 right-0 z-50 top-20 m-auto w-1/2 h-2/3 rounded-lg text-ellipsis text-base"
      >
        <slot></slot>
      </div>
    </Teleport>
  </div>
</template>

<style scoped></style>
