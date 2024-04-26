<script setup>
import { ref } from "vue";
import Taskdetail from "../components/Taskdetail.vue";
import Tasktable from "../components/Tasktable.vue";

const showModal = ref(false);
const statusModal = (statusModal) => {
  showModal.value = statusModal;
};
</script>

<template>
  <div class="text-center py-5">
    <h1 class="text-4xl font-bold">ITB-Kradan-Kanban</h1>
    <h1 class="font-semibold">By KP-2</h1>
  </div>
  <!-- Table -->
  <div class="flex flex-col">
    <h1 class="mb-4 text-center text-2xl font-semibold italic">Task Listing</h1>
    <Tasktable @openModal="statusModal" />
  </div>
  <!-- Modal -->
  <Teleport to="#modal">
    <div
      v-show="showModal"
      class="absolute left-0 right-0 z-50 top-20 m-auto w-2/5 h-2/3 bg-slate-50 rounded-lg"
    >
      <Taskdetail @closeModal="statusModal" />
    </div>
  </Teleport>
</template>

<style scoped></style>
