<script setup>
defineEmits(["closeModal"]);
</script>

<template>
  <div class="flex flex-col p-5 text-black">
    <h1 class="m-2 text-3xl font-bold">Example Title</h1>
    <hr />
    <div class="flex my-5 mx-auto">
      <div class="flex flex-col">
        <h1 class="font-bold">Description</h1>
        <textarea
          class="bg-slate-200 p-2 w-96 h-96 resize-none rounded-lg focus:bg-slate-300 shadow-lg"
        ></textarea>
      </div>
      <div class="flex flex-col m-2">
        <div class="flex flex-col m-1">
          <h1 class="font-bold">Assignees</h1>
          <textarea
            class="bg-slate-200 p-2 w-52 h-40 rounded-lg resize-none focus:bg-slate-300 shadow-lg"
          ></textarea>
        </div>
        <div class="flex flex-col m-2">
          <h1 class="font-bold">Status</h1>
          <select
            class="select select-bordered w-full max-w-xs bg-slate-200 focus:bg-slate-300 shadow-lg"
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
    <div class="flex justify-end m-2 mt-4">
      <button
        class="m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-green-500 hover:bg-slate-200 text-slate-200 hover:text-green-500 hover:outline hover:outline-green-500"
      >
        Ok
      </button>
      <button
        @click="$emit('closeModal', false)"
        class="m-1 p-2 w-14 font-bold rounded-md transition delay-80 bg-rose-500 hover:bg-slate-200 text-slate-200 hover:text-rose-500 hover:outline hover:outline-rose-500"
      >
        Close
      </button>
    </div>
  </div>
</template>

<style scoped></style>
