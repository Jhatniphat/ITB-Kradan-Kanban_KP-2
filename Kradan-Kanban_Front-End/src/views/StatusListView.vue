<script setup>
import { onMounted, ref } from "vue";
import AddStatusModal from "@/components/AddStatusModal.vue";
import Modal from "@/components/Modal.vue";
import { getAllStatus } from "@/lib/fetchUtils";

const showAddModal = ref(false);
const error = ref(null)
const status = ref(null)
const loading = ref(false)

const closeAddModal = (res) => {
  showAddModal.value = false;
  if (res === null) return 0;
  if ("id" in res)
    showToast({ status: "success", msg: "Add task successfuly" });
  else showToast({ status: "error", msg: "Add task Failed" });
};
onMounted(() => {
  fetchStatusData()
})
async function fetchStatusData(id) {
  if (id !== undefined) {
    openModal(id)
  }
  error.value = status.value = null
  loading.value = true;
  try {
    status.value = await getAllStatus();
  } catch (err) {
    error.value = err.toString();
  } finally{
    loading.value = false
    console.table(status.value)
  }
}
</script>

<template>
  <!-- NavBar -->
  <div class="navbar bg-base-300 sticky top-0 z-40 flex justify-between">
    <div class="navbar-start p-4">
      <div class="flex flex-col text-center ml-4 italic">
        <h1 class="text-xl font-bold">ITB-Kradan-Kanban</h1>
        <h1 class="text-sm">By KP-2</h1>
      </div>
    </div>
    <!-- Add Status -->
    <div class="navbar-end">
      <button
        class="btn btn-square btn-outline w-16"
        @click="showAddModal = true"
      >
        ADD STATUS
      </button>
    </div>
  </div>

  <!-- Home Button -->
  <div class="text-base breadcrumbs flex p-10">
    <ul>
      <li><RouterLink to="/task">Home</RouterLink></li>
      <li class="text-base font-semibold">Task Status</li>
    </ul>
  </div>

  <!-- content -->
  <div class="opacity">
    <div class="flex flex-col">
      <!-- Table -->
      <table
        class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto my-5 text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1"
      >
        <!-- Head -->
        <thead>
          <tr>
            <th>No</th>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <!-- Listing -->
        <tbody>
          <tr v-if="status === null">
            <td colspan="4">Waiting For Data</td>
          </tr>
          <tr
            v-if="status !== null"
            v-for="(status, index) in status"
            :key="status.id"
            class="itbkk-item hover"
          >
            <td>{{ index + 1 }}</td>
            <td class="itbkk-status-name">
              {{ status.name }}
            </td>
            <td class="itbkk-status-description">
              {{ status.description }}
            </td>
            <td class="itbkk-action-button">
              <button class="itbkk-button-edit btn m-2">Edit</button>
              <button class="itbkk-button-delete btn m-2">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Add Status Modal-->
  <Modal :show-modal="showAddModal">
    <AddStatusModal @closeModal="closeAddModal" />
  </Modal>
</template>

<style scoped></style>
