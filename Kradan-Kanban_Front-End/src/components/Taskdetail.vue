<script setup>
import { onMounted, ref, watch } from "vue"
import { getTaskById } from "../lib/fetchUtils.js"
import { useRoute } from "vue-router"
import router from "@/router"
defineEmits(["closeModal"])
const props = defineProps({
  taskId: {
    type: Number,
    require: true,
  },
})

const loading = ref(false)
const taskDetail = ref(null)
const error = ref(null)

watch(() => props.taskId, fetchData, { immediate: true })

async function fetchData(id) {
  error.value = taskDetail.value = null
  loading.value = true
  try {
    // replace `getPost` with your data fetching util / API wrapper
    taskDetail.value = await getTaskById(id)
    if (taskDetail.value == 404) {
      router.push("/task")
    }
  } catch (err) {
    error.value = err.toString()
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg">
    <h1 class="m-2 text-3xl font-bold" v-if="loading === true">
      Loading Data For TaskId = {{ props.id }}
    </h1>
    <h1
      class="itbkk-title m-2 text-2xl font-bold text-wrap break-all"
      v-if="loading === false && error === null"
    >
      {{ taskDetail.title }}
    </h1>
    <hr />
    <div class="flex my-5 mx-auto" v-if="loading === false && error === null">
      <div class="flex flex-col">
        <h1 class="font-bold">Description</h1>
        <textarea
          class="itbkk-description bg-slate-200 p-2 w-96 h-96 resize-none rounded-lg focus:bg-slate-300 shadow-lg"
          :style="{
            fontStyle: taskDetail.taskDescription ? 'normal' : 'italic',
          }"
          >{{
            taskDetail.taskDescription === null
              ? "No Description Provided"
              : taskDetail.taskDescription
          }}</textarea
        >
        <!-- <p
          class="itbkk-description m-2 text-wrap break-all"
          :style="{
            fontStyle: taskDetail.taskDescription ? 'normal' : 'italic',
          }"
        >
          {{
            taskDetail.taskDescription === null
              ? "No Description Provided"
              : taskDetail.taskDescription
          }}
        </p> -->
      </div>
      <div class="flex flex-col m-2">
        <div class="flex flex-col m-1">
          <h1 class="font-bold">Assignees</h1>
          <textarea
            class="itbkk-assignees bg-slate-200 p-2 h-32 resize-none rounded-lg focus:bg-slate-300 shadow-lg w-full"
            :style="{
              fontStyle: taskDetail.taskDescription ? 'normal' : 'italic',
            }"
            >{{
              taskDetail.taskAssignees === null
                ? "Unassigned"
                : taskDetail.taskAssignees
            }}</textarea
          >
          <!-- <p class="itbkk-assignees m-2 text-wrap break-all">
            {{ taskDetail.taskAssignees }}
          </p> -->
        </div>
        <div class="flex flex-col m-2">
          <h1 class="font-bold">Status</h1>
          <select
            class="itbkk-status select select-bordered w-full max-w-xs bg-slate-200 focus:bg-slate-300 shadow-lg"
          >
            <option disabled selected>status</option>
            <option>To Do</option>
            <option>In Progress</option>
            <option>Success</option>
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
    <div class="flex justify-end m-2 mt-4">
      <button
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
