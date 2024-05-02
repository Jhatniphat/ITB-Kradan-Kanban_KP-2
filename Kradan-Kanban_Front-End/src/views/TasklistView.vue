<script setup>
import { onBeforeMount, ref, watch } from "vue"
import { useRoute } from "vue-router"
import Taskdetail from "../components/Taskdetail.vue"
import {
  getAllTasks,
  getTaskById,
  addTask,
  editTask,
  deleteTask,
} from "../lib/fetchUtils.js"
import router from "@/router"

const showModal = ref(false)
const route = useRoute()

const loading = ref(false)
const allTasks = ref(null)
const error = ref(null)
const selectedid = ref(0)

const openModal = (id) => {
  selectedid.value = id
  showModal.value = true
}

async function fetchData(id) {
  if (id !== undefined) {
    openModal(id)
  }
  error.value = allTasks.value = null
  loading.value = true
  try {
    // replace `getPost` with your data fetching util / API wrapper
    allTasks.value = await getAllTasks()
  } catch (err) {
    error.value = err.toString()
  } finally {
    loading.value = false
  }
}

watch(() => route.params.id, fetchData, { immediate: true })

onBeforeMount(() => {
  if (route.params.id !== undefined) {
    selectedid.value = parseInt(route.params.id)
    showModal.value = true
  }
})
</script>

<template>
  <!-- NavBar -->
  <div class="navbar bg-base-300 sticky top-0">
    <div class="navbar-start p-4">
      <div class="flex flex-col text-center ml-4 italic">
        <h1 class="text-xl font-bold">ITB-Kradan-Kanban</h1>
        <h1 class="text-sm">By KP-2</h1>
      </div>
    </div>
  </div>

  <!-- Content -->

  <!-- Table -->
  <div class="flex flex-col">
    <!-- Add button -->
    <div class="flex justify-end mt-5 mx-auto">
      <button class="btn btn-square btn-outline w-16">+ ADD</button>
    </div>
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
              <div tabindex="0" role="button" class="btn m-1">Click</div>
              <ul
                tabindex="0"
                class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52"
              >
                <li><a>Edit</a></li>
                <li><a>Delete</a></li>
              </ul>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- <Tasktable/> -->
  </div>
  <!-- Modal -->
  <Teleport to="#modal">
    <div
      v-if="showModal"
      class="absolute left-0 right-0 z-50 top-20 m-auto w-1/2 h-2/3 bg-slate-50 rounded-lg"
    >
      <Taskdetail
        :taskId="parseInt(selectedid)"
        @closeModal="showModal = false"
      />
    </div>
  </Teleport>
  <Teleport to="#modal"> </Teleport>
</template>

<style scoped></style>
