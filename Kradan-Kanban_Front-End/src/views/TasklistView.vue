<script setup>
import { onBeforeMount, ref , watch} from "vue";
import { useRoute } from 'vue-router'
import Taskdetail from "../components/Taskdetail.vue";
import Tasktable from "../components/Tasktable.vue";
import { getAllTasks, getTaskById } from "../lib/fetchUtils.js";
import router from "@/router";

const showModal = ref(false);
const route = useRoute();

const loading = ref(false)
const allTasks = ref(null)
const error = ref(null)
const selectedTaskId = ref(0)

const openModal = (taskId) => {
  showModal.value = true
  selectedTaskId.value = taskId
};

async function fetchData(id) {
  if(id !== undefined) {
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
    selectedTaskId.value = parseInt(route.params.id)
    showModal.value = true
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
      class="table table-lg">
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
        <tr v-if="allTasks !== null" v-for="task in allTasks" :key="task.taskId" class="hover">
          <th>{{ task.taskId }}</th>
          <td>
            <!-- <RouterLink :to="`/task/${task.taskId}`"> -->
            <button @click="router.push(`/task/${task.taskId}`)">
              {{ task.taskTitle }}
            </button>
          <!-- </RouterLink> -->
          </td>
          <td>{{ task.taskAssignees }}</td>
          <td>{{ task.taskStatus }}</td>
        </tr>

      </tbody>
    </table>
    <!-- <Tasktable/> -->
  </div>
  <!-- Modal -->
  <Teleport to="#modal">
    <div v-if="showModal" class="absolute left-0 right-0 z-50 top-20 m-auto w-2/5 h-2/3 bg-slate-50 rounded-lg">
      <Taskdetail :taskId="parseInt( selectedTaskId )" @closeModal="showModal = false" />
    </div>
  </Teleport>
</template>

<style scoped></style>
