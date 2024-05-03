<script setup>
import { onBeforeMount, ref, watch } from "vue";
import { useRoute } from "vue-router";
import Taskdetail from "../components/Taskdetail.vue";
import {
  getAllTasks,
  getTaskById,
  addTask,
  editTask,
  deleteTask,
} from "../lib/fetchUtils.js";
import router from "@/router";
import AddEdit from "../components/AddEdit.vue";
import Modal from "../components/Modal.vue";
import AddTaskModal from "@/components/AddTaskModal.vue";

const showDetailModal = ref(false);
const showAddEditModal = ref(false);
const showDeleteModal = ref(false);
const route = useRoute();

const loading = ref(false);
const allTasks = ref(null);
const error = ref(null);
const selectedid = ref(0);
const showAddModal = ref(false)
const toast = ref({ status: '', msg: '' })

const closeAddModal = (res) => {
  showAddModal.value = false
  if(res === null) return 0;
  if ('id' in res) showToast({ status: 'success', msg: 'Add task successfuly' })
  else showToast({ status: 'error', msg: 'Add task Failed' })
}

const showToast = (toastData) => {
  toast.value = toastData
  console.log(toastData)
  setTimeout(() => {
    toast.value = {... { status : ''} }
  }, 5000);
}
const deleteTaskTitle = ref("");

const deleteThisTask = async () => {
  await deleteTask(selectedid.value);
  showDeleteModal.value = false;
  window.location.reload();
};

const openDeleteModal = (taskTitle, id) => {
  deleteTaskTitle.value = taskTitle;
  selectedid.value = id;
  showDeleteModal.value = true;
};

const openAddEdit = (id) => {
  selectedid.value = id;
  showAddEditModal = true;
  showDetailModal.value = false;
};

const openModal = (id) => {
  selectedid.value = id;
  showDetailModal.value = true;
};

async function fetchData(id, path) {
  const editPath = `/task/${id}/edit`;
  if (path === editPath) {
    openAddEdit(id);
  }
  if (id !== undefined) {
    openModal(id);
  }

  error.value = allTasks.value = null;
  loading.value = true;
  try {
    // replace `getPost` with your data fetching util / API wrapper
    allTasks.value = await getAllTasks();
  } catch (err) {
    error.value = err.toString();
  } finally {
    loading.value = false;
  }
  console.log("Path:", path);
  console.log("Edit Path:", editPath);
}

watch(() => route.params.id, fetchData, { immediate: true });

onBeforeMount(() => {
  if (route.params.id !== undefined) {
    selectedid.value = parseInt(route.params.id);
    showDetailModal.value = true;
  }
});
</script>

<template>
  <!-- NavBar -->
  <div class="navbar bg-base-300 sticky top-0 z-40">
    <div class="navbar-start p-4">
      <div class="flex flex-col text-center ml-4 italic">
        <h1 class="text-xl font-bold">ITB-Kradan-Kanban</h1>
        <h1 class="text-sm">By KP-2</h1>
      </div>
    </div>
  </div>

  <!-- Content -->

  <div class="flex flex-col">
    <!-- Add button -->
    <div class="flex justify-end mt-5 mx-auto right-1">
      <button
        class="btn btn-square btn-outline w-16"
        @click="showAddModal = true"
      >
        + ADD
      </button>
    </div>
    <!-- Table -->
    <table
      class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto my-5 text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1"
    >
      <!-- head -->
      <thead>
        <tr>
          <th>Id</th>
          <th>Title</th>
          <th>Assignees</th>
          <th>Status</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <!-- Listing -->
        <tr v-if="allTasks === null">
          <td colspan="4">Waiting For Data</td>
        </tr>
        <tr v-if="allTasks !== null" v-for="task in allTasks" :key="task.id" class="itbkk-item hover">
          <th>{{ task.id }}</th>
          <td class="itbkk-title">
            <!-- <RouterLink :to="`/task/${task.id}`"> -->
            <button @click="router.push(`/task/${task.id}`)">
              {{ task.title }}
            </button>
            <!-- </RouterLink> -->
          </td>
          <td
            class="itbkk-assignees"
            :style="{
              fontStyle: task.assignees ? 'normal' : 'italic',
              color: task.assignees ? '' : 'gray',
            }"
          >
            {{
              task.assignees === null || task.assignees == ""
                ? "Unassigned"
                : task.assignees
            }}
          </td>
          <td class="itbkk-status">{{ task.status }}</td>
          <td class="">
            <div class="dropdown dropdown-bottom dropdown-end">
              <div tabindex="0" role="button" class="btn m-1">Click</div>
              <ul
                tabindex="0"
                class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52"
              >
                <li>
                  <a @click="showAddEditModal = true">Edit</a>
                </li>
                <li>
                  <a @click="openDeleteModal(task.title, task.id)">Delete</a>
                </li>
              </ul>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- <Tasktable/> -->
  </div>

  <!-- Modal -->
  <!-- DetailsModal -->
  <Modal :show-modal="showDetailModal">
    <Taskdetail
      :taskId="parseInt(selectedid)"
      @closeModal="showDetailModal = false"
  /></Modal>

  <!-- EditModal -->
  <Modal :show-modal="showAddEditModal">
    <AddEdit
      :taskId="parseInt(selectedid)"
      @closeModal="showAddEditModal = false"
    />
  </Modal>
  <!-- DeleteModal -->
  <Modal :showModal="showDeleteModal">
    <h1 class="m-2 text-2xl font-bold">Delete</h1>
    <hr />
    <h1 class="font-semibold text-xl m-2">
      Do you want to delete the task "{{ deleteTaskTitle }}"
    </h1>
    <hr />
    <div class="flex justify-end m-2 mt-4">
      <button
        @click="deleteThisTask()"
        class="itbkk-button m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-green-500 hover:bg-slate-200 text-slate-200 hover:text-green-500 hover:outline hover:outline-green-500"
      >
        Ok
      </button>
      <button
        @click="showDeleteModal = false"
        class="itbkk-button m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-rose-500 hover:bg-slate-200 text-slate-200 hover:text-rose-500 hover:outline hover:outline-rose-500"
      >
        Close
      </button>
    </div>
  </Modal>

  <Teleport to="#modal">
    <div class="absolute left-0 right-0 z-50 top-20 m-auto w-1/2 h-2/3 bg-slate-50 rounded-lg" v-if="showAddModal">
      <AddTaskModal @closeModal="closeAddModal" />
    </div>
  </Teleport>
  <div class="toast">
    <div role="alert" class="alert" :class=" `alert-${toast.status}` " v-if="toast.status !== '' ">
      <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24" v-if="toast.status === 'success' ">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
          d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
      <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24" v-if="toast.status === 'error' ">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
          d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
      <span>{{ toast.msg }}</span>
    </div>
  </div>

  
</template>

<style scoped>
::backdrop {
  background-image: linear-gradient(45deg,
      magenta,
      rebeccapurple,
      dodgerblue,
      green);
  opacity: 0.75;
}
</style>
