<script setup>
import { onBeforeMount, ref, watch } from "vue"
import { useRoute } from "vue-router"
import Taskdetail from "../components/Taskdetail.vue"
import Tasktable from "../components/Tasktable.vue"
import { getAllTasks, getTaskById } from "../lib/fetchUtils.js"
import router from "@/router"
import AddTaskModal from "@/components/AddTaskModal.vue"

const showModal = ref(false)
const route = useRoute()

const loading = ref(false);
const allTasks = ref(null);
const error = ref(null);
const selectedid = ref(0);
const showAddModal = ref(true)
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

const openModal = (id) => {
  selectedid.value = id;
  showModal.value = true;
};

async function fetchData(id) {
  if (id !== undefined) {
    openModal(id);
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
    selectedid.value = parseInt(route.params.id);
    showModal.value = true;
  }
})
</script>

<template>
  <div class="text-center py-5">
    <h1 class="text-4xl font-bold">ITB-Kradan-Kanban</h1>
    <h1 class="font-semibold">By KP-2</h1>
  </div>
  <!-- Table -->
  <div class="flex flex-col">
    <h1 class="mb-4 text-center text-2xl font-semibold italic">Task Listing</h1>
    <table
      class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1">
      <!-- head -->
      <thead>
        <tr>
          <th>Id</th>
          <th>Title</th>
          <th>Assignees</th>
          <th>Status</th>
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
          <td class="itbkk-assignees" :style="{
            fontStyle: task.assignees ? 'normal' : 'italic',
            color: task.assignees ? '' : 'gray'
          }">
            {{
              task.assignees === null || task.assignees == '' ? "Unassigned" : task.assignees
            }}
          </td>
          <td class="itbkk-status">{{ task.status }}</td>
        </tr>
      </tbody>
    </table>
    <!-- <Tasktable/> -->
  </div>
  <!-- Modal -->
  <Teleport to="#modal">
    <div v-if="showModal" class="absolute left-0 right-0 z-50 top-20 m-auto w-1/2 h-2/3 bg-slate-50 rounded-lg">
      <Taskdetail :taskId="parseInt(selectedid)" @closeModal="showModal = false" />
    </div>
    <div class="absolute left-0 right-0 z-50 top-20 m-auto w-1/2 h-2/3 bg-slate-50 rounded-lg" v-if="showAddModal">
      <AddTaskModal @closeModal="closeAddModal" />
    </div>
  </Teleport>

  <div class="toast">
    <div role="alert" class="alert alert-error" :class=" `alert-${toast.status}` " v-if="toast.status !== '' ">
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
