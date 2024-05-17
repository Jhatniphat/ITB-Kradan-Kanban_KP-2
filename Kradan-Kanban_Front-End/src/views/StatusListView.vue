<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import AddStatusModal from "@/components/Status/AddStatusModal.vue";
import EditStatus from "@/components/Status/EditStatus.vue";
import DeleteStatus from "@/components/Status/DeleteStatus.vue";
import Modal from "@/components/Modal.vue";
import router from "@/router";
import {useStatusStore} from "@/stores/status.js";

// ! ================= Variable ======================
// ? ----------------- Store and Route ---------------
const statusStore = useStatusStore();
const route = useRoute();

// ? ----------------- Modal ---------------
const toast = ref({status: "", msg: ""});
const showAddModal = ref(false);
const showEdit = ref(false);
const showDelete = ref(false);

// ? ----------------- Common -------------------------
const selectedId = ref(0);
const deleteTitle = ref("");
const error = ref(null);
const status = ref(null);
const loading = ref(false);

onMounted(() => {
  fetchStatusData();
  if (route.params.id !== undefined) {
    selectedId.value = parseInt(route.params.id);
    showEdit.value = true;
    console.log(selectedId.value, showEdit.value);
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

// ! ================= Modal ======================
const showToast = (toastData) => {
  toast.value = toastData;
  console.log(toastData);
  setTimeout(() => {
    toast.value = {...{status: ""}};
  }, 5000);
};

const closeAddModal = (res) => {
  showAddModal.value = false;
  if (res === null) return 0;
  if ("id" in res) {
    showToast({status: "success", msg: "Add task successfuly"});
    statusStore.addStoreStatus(res);
  } else showToast({status: "error", msg: "Add task Failed"});
};

const openEdit = (id) => {
  selectedId.value = id;
  showEdit.value = true;
  router.push(`/status/${id}`);
};

const closeEdit = (res) => {
  showEdit.value = false;
  if (res === null) return 0;
  if (res === 404) showToast({status: "error", msg: "An error has occurred, the status does not exist"});
  if ("id" in res) {
    showToast({status: "success", msg: "Edit task successfuly"});
    statusStore.editStoreStatus(res)
  } else showToast({status: "error", msg: "Edit task Failed"});
};

const openDelete = (id, title) => {
  selectedId.value = id;
  deleteTitle.value = title;
  showDelete.value = true;
};

const closeDelete = (res) => {
  showDelete.value = false;
  if (res === null) return 0;
  // if (res === 404)
  if (typeof (res) === "object") {
    statusStore.deleteStoreStatus(res)
    showToast({status: "success", msg: "Delete task successfuly"});
  } else {
    showToast({status: "error", msg: "Delete task Failed ,Please restart page"} , 6000)
  }
  ;
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
      <button class="itbkk-button-add btn btn-square btn-outline w-16" @click="showAddModal = true">
        Add Status
      </button>
    </div>
  </div>

  <!-- Home Button -->
  <div class="text-base breadcrumbs flex p-10">
    <ul>
      <li @click="router.push('/task')" class="cursor-pointer">Home</li>
      <li class="text-base font-semibold">Task Status</li>
    </ul>
  </div>

  <!-- content -->
  <div class="opacity">
    <div class="flex flex-col">
      <!-- Table -->
      <table
          class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto my-5 text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1">
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
        <tr v-if="status !== null" v-for="(status, index) in status" :key="status.id" class="itbkk-item hover">
          <td>{{ index + 1 }}</td>
          <td class="itbkk-status-name break-all">
            {{ status.name }}
          </td>
          <td class="itbkk-status-description break-all" :style="{
              fontStyle: status.description ? 'normal' : 'italic',
              color: status.description ? '' : 'gray',
            }">
            {{
              status.description === null || status.description === ""
                  ? "No description is provided"
                  : status.description
            }}
          </td>
          <td v-if="status.name !== 'No Status'" class="itbkk-action-button">
            <button class="itbkk-button-edit btn m-2" @click="openEdit(status.id)">
              Edit
            </button>
            <button class="itbkk-button-delete btn m-2" @click="openDelete(status.id, status.name)">
              Delete
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Modal -->
  <!-- Add Status Modal-->
  <Modal :show-modal="showAddModal">
    <AddStatusModal @closeModal="closeAddModal"/>
  </Modal>
  <!-- Edit Modal -->
  <Modal :show-modal="showEdit">
    <EditStatus :status-id="parseInt(selectedId)" @close-modal="closeEdit"/>
  </Modal>
  <!-- Delete Modal -->
  <Modal :show-modal="showDelete">
    <DeleteStatus :delete-id="parseInt(selectedId)" :deleteTitle="deleteTitle" @close-modal="closeDelete"/>
  </Modal>
  <!-- Toast -->
  <div class="toast">
    <div role="alert" class="alert" :class="`alert-${toast.status}`" v-if="toast.status !== ''">
      <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"
           v-if="toast.status === 'success'">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
      </svg>
      <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"
           v-if="toast.status === 'error'">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
      </svg>
      <span class="itbkk-message">{{ toast.msg }}</span>
    </div>
  </div>
</template>

<style scoped></style>
