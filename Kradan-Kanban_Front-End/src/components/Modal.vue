<script setup>
defineProps(["showModal"]);
</script>

<template>
  <div>
    <Teleport id="delete" to="#modal">
      <div
        v-if="showModal"
        class="absolute left-0 right-0 z-50 top-44 m-auto w-1/3 h-auto bg-base-200 rounded-lg p-2 shadow-xl text-ellipsis text-base border-2 border-slate-500"
      >
        <slot></slot>
      </div>
    </Teleport>
  </div>
</template>

<style scoped></style>
