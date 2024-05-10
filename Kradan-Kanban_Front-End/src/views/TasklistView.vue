<script setup>
import { onBeforeMount, ref, watch } from "vue";
import { useRoute } from "vue-router";
import Taskdetail from "../components/Tasks/Taskdetail.vue";
import { getAllTasks, deleteTask } from "../lib/fetchUtils.js";
import router from "@/router";
import Modal from "../components/Modal.vue";
import AddTaskModal from "@/components/Tasks/AddTaskModal.vue";

const showDetailModal = ref(false);
const showDeleteModal = ref(false);
const route = useRoute();

const loading = ref(false);
const allTasks = ref(null);
const error = ref(null);
const selectedid = ref(0);
const showAddModal = ref(false);
const toast = ref({ status: "", msg: "" });

const openEditMode = (id) => {
  showDetailModal.value = true;
  router.push(`/task/${id}`);
};

const closeAddModal = (res) => {
  showAddModal.value = false;
  if (res === null) return 0;
  if ("id" in res)
    showToast({ status: "success", msg: "Add task successfuly" });
  else showToast({ status: "error", msg: "Add task Failed" });
};

const closeEditModal = (res) => {
  showDetailModal.value = false;
  if (res === null) return 0;
  // if ("id" in res)
  //   showToast({ status: "success", msg: "Edit task successfuly" });
  // else showToast({ status: "error", msg: "Edit task Failed" });
};

const showToast = (toastData) => {
  toast.value = toastData;
  console.log(toastData);
  setTimeout(() => {
    toast.value = { ...{ status: "" } };
  }, 5000);
};

const deleteTaskTitle = ref("");

const deleteThisTask = async () => {
  let res;
  try {
    res = await deleteTask(selectedid.value);
    if ("id" in res)
      showToast({ status: "success", msg: "Delete task successfuly" });
    else showToast({ status: "error", msg: "Delete task Failed" });
  } catch (error) {
    console.log(error);
  } finally {
    showDeleteModal.value = false;
  }
};

const openDeleteModal = (taskTitle, id) => {
  deleteTaskTitle.value = taskTitle;
  selectedid.value = id;
  showDeleteModal.value = true;
};

const openModal = (id) => {
  selectedid.value = id;
  showDetailModal.value = true;
};

async function fetchData(id) {
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
    <!-- Add button -->
    <div class="navbar-end">
      <button
        class="btn btn-square btn-outline w-16 m-2"
        @click="showAddModal = true"
      >
        + ADD
      </button>
      <div class="manage-status">
        <button
          @click="router.push('/status')"
          class="btn btn-square btn-outline w-20 m-2"
        >
          Manage Status
        </button>
      </div>
    </div>
    <!-- Manage Status Button -->
  </div>

  <!-- Content -->
  <div class="opacity">
    <div class="flex flex-col">
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
          <tr
            v-if="allTasks !== null"
            v-for="task in allTasks"
            :key="task.id"
            class="itbkk-item hover"
          >
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
                <div tabindex="0" role="button" class="btn m-1">
                  <svg
                    class="swap-off fill-current"
                    xmlns="http://www.w3.org/2000/svg"
                    width="32"
                    height="32"
                    viewBox="0 0 512 512"
                  >
                    <path
                      d="M64,384H448V341.33H64Zm0-106.67H448V234.67H64ZM64,128v42.67H448V128Z"
                    />
                  </svg>
                </div>
                <ul
                  tabindex="0"
                  class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52"
                >
                  <li>
                    <a @click="openEditMode(task.id)">Edit</a>
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
    </div>

    <!-- Modal -->
    <!-- DetailsModal -->
    <!-- EditModal -->
    <Modal :show-modal="showDetailModal">
      <Taskdetail :taskId="parseInt(selectedid)" @closeModal="closeEditModal()"
    /></Modal>
    <!-- Add Modal -->
    <Modal :show-modal="showAddModal">
      <AddTaskModal @closeModal="closeAddModal" />
    </Modal>

    <!-- DeleteModal -->
    <Modal :showModal="showDeleteModal">
      <div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg w-full">
        <h1 class="m-2 pb-4 text-2xl font-bold">
          DELETE: {{ deleteTaskTitle }}
        </h1>
        <hr />
        <h1 class="itbkk-message font-semibold text-xl p-8">
          <!-- Do you want to delete the task "{{ deleteTaskTitle }}" -->
          ARE YOU SURE TO DELETE THIS TASK ?
        </h1>
        <hr />
        <div class="flex flex-row-reverse gap-4 mt-5">
          <button
            @click="showDeleteModal = false"
            class="itbkk-button-cancel btn btn-outline btn-error basis-1/6"
          >
            Close
          </button>
          <button
            @click="deleteThisTask()"
            class="itbkk-button-confirm btn btn-outline btn-success basis-1/6"
          >
            {{ loading ? "" : "Confirm" }}
            <span
              class="loading loading-spinner text-success"
              v-if="loading"
            ></span>
          </button>
        </div>
      </div>
    </Modal>

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
  </div>
</template>

<style scoped>
/* .opacity {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
  display: flex;
  justify-content: center;
  align-items: center;
} */
::backdrop {
  background-image: linear-gradient(
    45deg,
    magenta,
    rebeccapurple,
    dodgerblue,
    green
  );
  opacity: 0.75;
}
</style>
