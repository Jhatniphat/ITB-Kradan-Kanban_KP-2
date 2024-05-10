<script setup>
import { onMounted, ref } from "vue";
import { getAllStatus } from "@/lib/fetchUtils";
import { useRoute } from "vue-router";
import AddStatusModal from "@/components/Status/AddStatusModal.vue";
import EditStatus from "@/components/Status/EditStatus.vue";
import Modal from "@/components/Modal.vue";
import router from "@/router";
import { useStatusStore } from "@/stores/status.js";

const statusStore = useStatusStore();
const route = useRoute();
const selectedid = ref(0);
const showAddModal = ref(false);
const showEdit = ref(false);
const showDelete = ref(false);
const error = ref(null);
const status = ref(null);
const loading = ref(false);
const toast = ref({ status: "", msg: "" });

onMounted(() => {
  fetchStatusData();
  if (route.params.id !== undefined) {
    selectedid.value = parseInt(route.params.id);
    showEdit.value = true;
    console.log(selectedid.value, showEdit.value);
  }
});

async function fetchStatusData(id) {
  if (id !== undefined) {
    openEdit(id);
  }
  error.value = status.value = null;
  loading.value = true;
  try {
    status.value = await statusStore.getAllStatus();
  } catch (err) {
    error.value = err.toString();
  } finally {
    loading.value = false;
    console.table(status.value);
  }
}

const showToast = (toastData) => {
  toast.value = toastData;
  console.log(toastData);
  setTimeout(() => {
    toast.value = { ...{ status: "" } };
  }, 5000);
};

const closeAddModal = (res) => {
  showAddModal.value = false;
  if (res === null) return 0;
  if ("id" in res){
    showToast({ status: "success", msg: "Add task successfuly" });
    statusStore.addStoreStatus(res);
  } else showToast({ status: "error", msg: "Add task Failed" });
};

const openEdit = (id) => {
  selectedid.value = id;
  showEdit.value = true;
  router.push(`/status/${id}`);
};

const closeEdit = (res) => {
  showEdit.value = false;
  if (res === null) return 0;
  if ("id" in res)
    showToast({ status: "success", msg: "Edit task successfuly" });
  else showToast({ status: "error", msg: "Edit task Failed" });
};
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
        Add Status
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
            <td class="itbkk-status-name break-all">
              {{ status.name }}
            </td>
            <td class="itbkk-status-description break-all">
              {{ status.description }}
            </td>
            <td class="itbkk-action-button">
              <button
                class="itbkk-button-edit btn m-2"
                @click="openEdit(status.id)"
              >
                Edit
              </button>
              <button class="itbkk-button-delete btn m-2">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Modal -->
  <!-- Add Status Modal-->
  <Modal :show-modal="showAddModal">
    <AddStatusModal @closeModal="closeAddModal" />
  </Modal>
  <!-- Edit Modal -->
  <Modal :show-modal="showEdit">
    <EditStatus :status-id="parseInt(selectedid)" @close-modal="closeEdit" />
  </Modal>
  <!-- Delete Modal -->
  <!-- <Modal :show-modal="showDelete"> </Modal> -->
  <!-- Toast -->
  <div class="toast">
    <div
      role="alert"
      class="alert"
      :class="`alert-${toast.status}`"
      v-if="toast.status !== ''"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="stroke-current shrink-0 h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        v-if="toast.status === 'success'"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
        />
      </svg>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="stroke-current shrink-0 h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        v-if="toast.status === 'error'"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
        />
      </svg>
      <span>{{ toast.msg }}</span>
    </div>
  </div>
</template>

<style scoped></style>
