<script setup>
import { ref, watch } from "vue";
import { getTaskById, editTask } from "../lib/fetchUtils.js";

import router from "@/router";

defineEmits(["closeModal"]);
const props = defineProps({
  taskId: {
    type: Number,
    require: true,
  },
});

const editMode = ref(false); // Track if component is in edit mode
const statusList = ["To Do", "Doing", "Done"];
const canSave = ref(false);
const loading = ref(false);
const taskDetail = ref(null);
const originalTask = ref(null); // Hold a copy of the original task details
const error = ref(null);
const Errortext = ref({
  title: "",
  description: "",
  assignees: "",
});

watch(() => props.taskId, fetchData, { immediate: true });

watch(
  taskDetail,
  (newVal, oldVal) => {
    if (
      !loading.value &&
      JSON.stringify(newVal) !== JSON.stringify(originalTask.value)
    ) {
      canSave.value = true; // Enable save button if task details are different
    } else {
      canSave.value = false; // Disable save button if task details are unchanged or still loading
    }
  },
  { deep: true }
);
// watch(taskDetail.value, () => {
//   if (taskDetail.value.title.trim().length > 100)
//     Errortext.value.title = `Title can't long more than 100 character`;
//   else if (taskDetail.value.title.trim().length == 0)
//     Errortext.value.title = `Title can't be empty`;
//   else Errortext.value.title = "";

//   if (taskDetail.value.description.trim().length > 500)
//     Errortext.value.description = `Description can't long more than 500 character`;
//   else Errortext.value.description = "";

//   if (taskDetail.value.assignees.trim().length > 30)
//     Errortext.value.assignees = `Assignees can't long more than 30 character`;
//   else Errortext.value.assignees = "";
//   // ? disabled or enabled save btn
//   canSave.value =
//     Errortext.value.title === "" &&
//     Errortext.value.description === "" &&
//     Errortext.value.assignees === "";
//   console.log(
//     Errortext.value.title,
//     Errortext.value.description,
//     Errortext.value.assignees
//   );
// });

async function fetchData(id) {
  error.value = taskDetail.value = null;
  loading.value = true;
  try {
    const originalTaskDetails = await getTaskById(id);
    originalTask.value = { ...originalTaskDetails }; // Create a copy of the original task details
    taskDetail.value = { ...originalTaskDetails };
    // replace `getPost` with your data fetching util / API wrapper
    if (taskDetail.value == 404) {
      router.push("/task");
    }
  } catch (err) {
    error.value = err.toString();
  } finally {
    loading.value = false;
  }
}
async function saveTask() {
  loading.value = true;
  try {
    const updatedTask = await editTask(props.taskId, taskDetail.value);
    taskDetail.value = updatedTask;
    editMode.value = false;
  } catch (error) {
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg">
    <div v-if="!editMode">
      <h1 class="m-2 text-3xl font-bold" v-if="loading === true">
        Loading Data For TaskId = {{ props.taskId }}
      </h1>
      <h1
        class="itbkk-title m-2 text-2xl font-bold text-wrap break-all"
        v-if="loading === false && error === null"
      >
        {{ taskDetail.title }}
      </h1>
    </div>
    <div v-if="editMode">
      <h1 class="m-2 text-3xl font-bold" v-if="loading === true">
        Loading Data For TaskId = {{ props.taskId }}
      </h1>
      <div v-if="loading === false && error === null">
        <h1 class="m-2 text-2xl font-bold text-wrap break-all">Edit task</h1>
        <hr />
        <h1 class="itbkk-title m-2 font-bold text-wrap break-all">Title</h1>
        <input
          v-model="taskDetail.title"
          type="text"
          placeholder="Type here"
          class="itbkk-title input input-bordered w-full"
        />
        <span v-if="Errortext.title !== ''" class="label-text-alt text-error">{{
          Errortext.title
        }}</span>
      </div>
    </div>
    <hr />
    <div class="flex my-5 mx-auto" v-if="loading === false && error === null">
      <div class="flex flex-col">
        <h1 class="font-bold">Description</h1>
        <textarea
          v-model.trim="taskDetail.description"
          class="itbkk-description p-2 w-96 h-96 textarea textarea-bordered bg-slate-200 focus:bg-slate-300"
          :style="{
            fontStyle: taskDetail.description ? 'normal' : 'italic',
          }"
          :class="taskDetail.description === '' ? 'italic text-gray-600' : ''"
          >{{
            taskDetail.description == "" || taskDetail.description === null
              ? "No Description Provided"
              : taskDetail.description
          }}</textarea
        >
        <span
          v-if="Errortext.description !== ''"
          class="label-text-alt text-error"
        >
          {{ Errortext.description }}</span
        >
      </div>
      <div class="flex flex-col m-2 mt-0">
        <div class="flex flex-col m-1">
          <h1 class="font-bold">Assignees</h1>
          <textarea
            class="itbkk-assignees textarea textarea-bordered bg-slate-200 focus:bg-slate-300"
            :style="{
              fontStyle: taskDetail.assignees ? 'normal' : 'italic',
            }"
            :class="
              taskDetail.assignees === '' || taskDetail.assignees === null
                ? 'italic text-gray-600'
                : ''
            "
            >{{
              taskDetail.assignees == "" || taskDetail.assignees === null
                ? "Unassigned"
                : taskDetail.assignees
            }}</textarea
          >
          <span
            v-if="Errortext.assignees !== ''"
            class="label-text-alt text-error"
          >
            {{ Errortext.assignees }}</span
          >
        </div>
        <div class="flex flex-col m-2">
          <h1 class="font-bold">Status</h1>
          <select
            class="itbkk-status select select-bordered w-full max-w-xs bg-base-200 focus:bg-base-300 shadow-lg"
            v-model="taskDetail.status"
          >
            <option value="No Status" selected>No Status</option>
            <option v-for="status in statusList" :value="status">
              {{ status }}
            </option>
          </select>
        </div>
        <div class="mt-2 text-sm text-black">
          <div
            class="flex flex-row justify-around border-solid border-slate-400"
          >
            <h1 class="font-bold">TimeZone</h1>
            <h1 class="itbkk-timezone font-semibold">
              {{ Intl.DateTimeFormat().resolvedOptions().timeZone }}
            </h1>
          </div>
          <div class="flex flex-row justify-around">
            <h1 class="font-bold">Created On</h1>
            <h1 class="itbkk-created-on font-semibold">
              {{ taskDetail.createdOn }}
            </h1>
          </div>
          <div class="flex flex-row justify-around">
            <h1 class="font-bold">Updated On</h1>
            <h1 class="itbkk-updated-on font-semibold">
              {{ taskDetail.updatedOn }}
            </h1>
          </div>
        </div>
      </div>
    </div>

    <div class="flex my-5 mx-auto" v-if="error !== null">
      <div>
        <h1>{{ error }}</h1>
      </div>
    </div>

    <hr />
    <div class="flex flex-row-reverse gap-4 mt-5">
      <!-- Cancel button -->
      <button
        class="btn btn-outline btn-error basis-1/6"
        @click="router.push(`/task`), $emit('closeModal', false)"
      >
        Cancel
      </button>
      <!-- Toggle edit mode button -->
      <button
        v-if="!editMode"
        class="btn btn-outline btn-primary basis-1/6"
        @click="editMode = true"
      >
        Edit
      </button>
      <!-- Save changes button -->
      <button
        v-if="editMode"
        class="btn btn-outline btn-success basis-1/6"
        :disabled="!canSave"
        @click="saveTask"
      >
        {{ loading ? "" : "Save" }}
        <span
          class="loading loading-spinner text-success"
          v-if="loading"
        ></span>
      </button>
    </div>
  </div>
</template>

<style scoped></style>
