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

const loading = ref(false);
const taskDetail = ref(null);
const error = ref(null);

watch(() => props.taskId, fetchData, { immediate: true });

async function fetchData(id) {
  error.value = taskDetail.value = null;
  loading.value = true;
  try {
    // replace `getPost` with your data fetching util / API wrapper
    taskDetail.value = await getTaskById(id);
    if (taskDetail.value == 404) {
      router.push("/task");
    }
  } catch (err) {
    error.value = err.toString();
  } finally {
    loading.value = false;
  }
}
async function saveChanges(id, editedTask) {
  try {
    const updatedTask = await editTask(id, editedTask);
    if (updatedTask !== 404) {
      window.location.reload();
    } else {
    }
  } catch (error) {}
  console.log(editTask);
}
</script>

<template>
  <div class="flex flex-col p-2 text-base bg-base-50 rounded-lg">
    <h1 class="m-2 text-3xl font-bold" v-if="loading === true">
      Loading Data For TaskId = {{ props.id }}
    </h1>
    <h1
      class="itbkk-title m-2 text-2xl font-bold text-wrap break-all"
      v-if="loading === false && error === null"
    >
      {{ taskDetail.id === undefined ? "New Task" : "Edit Task" }}
    </h1>
    <hr />
    <div class="flex my-5 mx-auto" v-if="loading === false && error === null">
      <div class="flex flex-col">
        <!-- <div class="flex flex-col"> -->
        <h1 class="font-bold">Title</h1>
        <textarea
          class="p-2 w-96 h-9 textarea textarea-bordered bg-base-200 focus:bg-base-300"
          v-model.trim="taskDetail.title"
        ></textarea>
        <h1 class="font-bold">Description</h1>
        <textarea
          v-model.trim="taskDetail.description"
          class="itbkk-description p-2 w-96 h-96 textarea textarea-bordered bg-base-200 focus:bg-base-300"
          :style="{
            fontStyle: taskDetail.description ? 'normal' : 'italic',
          }"
          :class="taskDetail.description === '' ? 'italic text-gray-600' : ''"
        ></textarea>
      </div>
      <div class="flex flex-col m-2 mt-0">
        <div class="flex flex-col m-1">
          <h1 class="font-bold">Assignees</h1>
          <textarea
            class="itbkk-assignees textarea textarea-bordered bg-base-200 focus:bg-base-300"
            v-model.trim="taskDetail.assignees"
            :style="{
              fontStyle: taskDetail.assignees ? 'normal' : 'italic',
            }"
            :class="
              taskDetail.assignees === '' || taskDetail.assignees === null
                ? 'italic text-gray-600'
                : ''
            "
          ></textarea>
          <!-- </div> -->
          <div class="flex flex-col m-2">
            <h1 class="font-bold">Status</h1>
            <select
              class="itbkk-status select select-bordered w-full max-w-xs bg-base-200 focus:bg-base-300 shadow-lg"
              v-model="taskDetail.status"
            >
              <option disabled selected>Status</option>
              <option value="No Status">No Status</option>
              <option value="To Do">To Do</option>
              <option value="Doing">Doing</option>
              <option value="Done">Done</option>
            </select>
          </div>
          <div class="mt-2 text-xs">
            <div
              class="flex flex-row justify-around border-solid border-slate-400"
            >
              <h1 class="font-bold">TimeZone</h1>
              <h1 class="itbkk-timezone font-semibold justify-end">
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
    </div>

    <div class="flex my-5 mx-auto" v-if="error !== null">
      <div>
        <h1>{{ error }}</h1>
      </div>
    </div>

    <hr />
    <div class="flex justify-end m-2 mt-4">
      <button
        @click="saveChanges(taskDetail.id, taskDetail.value)"
        class="itbkk-button m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-green-500 hover:bg-slate-200 text-slate-200 hover:text-green-500 hover:outline hover:outline-green-500"
      >
        Ok
      </button>
      <button
        @click="router.push(`/task`), $emit('closeModal', false)"
        class="itbkk-button m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-rose-500 hover:bg-slate-200 text-slate-200 hover:text-rose-500 hover:outline hover:outline-rose-500"
      >
        Close
      </button>
    </div>
  </div>
</template>

<style scoped></style>
