<script setup>
import { onMounted, ref } from "vue";
import Taskdetail from "../components/Taskdetail.vue";
import Tasktable from "../components/Tasktable.vue";

// import { getAllTasks, getTaskById } from "../lib/fetchUtils.js";

const allTask = ref({});

// onMounted(async () => {
//   allTask.value = await getAllTasks();
// });

const showModal = ref(false);
const taskIdModal = ref();
const statusModal = (statusModal, taskId) => {
  showModal.value = statusModal;
};

// Sample data
const sampleTask = ref([
  {
    taskId: 1,
    taskTitle:
      "TaskTitle1TaskTitle2TaskTitle3TaskTitle4TaskTitle5TaskTitle6TaskTitle7TaskTitle8TaskTitle9TaskTitle0",
    taskDescription:
      "Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti1Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti2Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti3Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti4Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti5",
    taskAssignees: "Assignees1Assignees2Assignees3",
    taskStatus: "No Status",
    timeZone: "Asia//Thailand",
    createdOn: "2024-04-27 00:12:55",
    updatedOn: "2024-04-27 00:12:55",
  },
  {
    taskId: 2,
    taskTitle: "Repository",
    taskDescription: "",
    taskAssignees: "",
    taskStatus: "To Do",
    timeZone: "Asia//Thailand",
    createdOn: "2024-04-27 00:12:55",
    updatedOn: "2024-04-27 00:12:55",
  },
  {
    taskId: 3,
    taskTitle: "ดาต$าเบส",
    taskDescription: "ສ້າງຖານຂໍ້ ມູ ນ",
    taskAssignees: "あなた、彼彼⼥(私ではありません)",
    taskStatus: "Doing",
    timeZone: "Asia//Thailand",
    createdOn: "2024-04-27 00:12:55",
    updatedOn: "2024-04-27 00:12:55",
  },
  {
    taskId: 4,
    taskTitle: "_Infrastructure_",
    taskDescription: "_Setup containers_",
    taskAssignees: " ไก*งวงกับเพนกวิน ",
    taskStatus: "Done",
    timeZone: "Asia//Thailand",
    createdOn: "2024-04-27 00:12:55",
    updatedOn: "2024-04-27 00:12:55",
  },
]);
</script>

<template>
  <div class="text-center py-5">
    <h1 class="text-4xl font-bold">ITB-Kradan-Kanban</h1>
    <h1 class="font-semibold">By KP-2</h1>
  </div>
  <!-- Table -->
  <div class="flex flex-col">
    <h1 class="mb-4 text-center text-2xl font-semibold italic">Task Listing</h1>
    <Tasktable :task="sampleTask" @openModal="statusModal" />
  </div>
  <!-- Modal -->
  <Teleport to="#modal">
    <div
      v-show="showModal"
      class="absolute left-0 right-0 z-50 top-20 m-auto w-2/5 h-2/3 bg-slate-50 rounded-lg"
    >
      <Taskdetail :task="sampleTask" @closeModal="statusModal" />
    </div>
  </Teleport>
</template>

<style scoped></style>
