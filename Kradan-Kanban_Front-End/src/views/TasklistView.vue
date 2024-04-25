<script setup>
import { ref } from "vue";
const showModal = ref(false);
</script>

<template>
  <div class="text-center py-5">
    <h1 class="text-4xl font-bold border-solid border-white">
      ITB-Kradan-Kanban
    </h1>
    <h1 class="font-semibold">By KP-2</h1>
  </div>

  <div class="flex flex-col">
    <h1 class="mb-4 text-center text-2xl font-semibold italic">Task Listing</h1>
    <table
      class="table table-zebra w-3/4 font-semibold mx-auto text-center text-base rounded-md border-solid border-white"
    >
      <!-- head -->
      <thead>
        <tr>
          <th></th>
          <th>Title</th>
          <th>Assignees</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <!-- Listing -->
        <tr v-for="list in Task" :key="list.id">
          <th>{{ list.id }}</th>
          <td class="hover:bg-slate-700">
            <button @click="showModal = true">{{ list.title }}</button>
          </td>
          <td>{{ list.assignees }}</td>
          <td>{{ list.status }}</td>
        </tr>
        <!-- Test -->
        <tr>
          <th>999</th>
          <td class="hover:bg-slate-700">
            <button @click="showModal = true">ສ້າງຖານຂໍ້ມູນ</button>
          </td>
          <td>あなた、彼、彼女 (私ではありません)</td>
          <td>Test</td>
        </tr>
      </tbody>
    </table>
  </div>
  <Teleport to="#modal">
    <div
      v-show="showModal"
      class="absolute left-0 right-0 top-20 m-auto w-2/5 h-2/3 bg-slate-50 rounded-lg"
    >
      <div class="flex flex-col p-5 text-black">
        <h1 class="m-2 text-3xl font-bold">Example Title</h1>
        <hr />
        <div class="flex m-2">
          <div class="flex flex-col">
            <h1 class="font-bold">Description</h1>
            <textarea class="bg-slate-200 p-2 w-96 h-96 rounded-lg"></textarea>
          </div>
          <div class="flex flex-col m-2">
            <div class="flex flex-col m-1">
              <h1 class="font-bold">Assignees</h1>
              <textarea
                class="bg-slate-200 p-2 w-52 h-40 rounded-lg"
              ></textarea>
            </div>
            <div class="flex flex-col m-2">
              <h1 class="font-bold">Status</h1>
              <select
                class="select select-bordered w-full max-w-xs bg-slate-200"
              >
                <option disabled selected>status</option>
                <option>To Do</option>
                <option>In Progress</option>
                <option>Success</option>
              </select>
            </div>
            <div class="justify-end text-black">
              <div
                class="flex flex-row justify-around border-solid border-slate-400"
              >
                <h1 class="font-bold">TimeZone</h1>
                <h1 class="font-semibold">Timezone here</h1>
              </div>
              <div class="flex flex-row justify-around">
                <h1 class="font-bold">Created On</h1>
                <h1 class="font-semibold">Created On here</h1>
              </div>
              <div class="flex flex-row justify-around">
                <h1 class="font-bold">Updated On</h1>
                <h1 class="font-semibold">Updated On here</h1>
              </div>
            </div>
          </div>
        </div>
        <hr />
        <div class="flex justify-end m-2">
          <button
            class="m-1 p-2 w-14 rounded-md bg-green-500 hover:bg-green-400 text-slate-200 font-bold"
          >
            Ok
          </button>
          <button
            @click="showModal = false"
            class="m-1 p-2 w-14 rounded-md bg-slate-500 text-slate-200 hover:bg-slate-400 font-bold"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped></style>
