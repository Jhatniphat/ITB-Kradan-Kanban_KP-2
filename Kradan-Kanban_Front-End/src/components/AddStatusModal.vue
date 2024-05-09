<script setup>
import { ref, watch } from "vue";
const emit = defineEmits(["closeModal"]);
const statusData = ref({
    name: "",
    description: ""
})

const Errortext = ref({
    name: "",
    description: ""
})

const loading = ref(false)
async function fetchStatusData() {
    statusData.value.name = statusData.value.name.trim();
    statusData.value.description = statusData.value.description.trim();
    loading.value = true;
    let res;
    // try {
    //     res = await add
    // }
}
</script>
 
<template>
<!-- div -->
<div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg w-full">
    <!-- title -->
    <label class="form-control w-full">
        <div class="label">
            <!-- Add Status Text -->
            <span class="label-text">Add Status</span>
        </div>
    </label>
    <hr />

    <!-- name -->
    <label class="form-control w-full">
      <div class="label">
        <!-- ? Head -->
        <span class="label-text">Title</span>
      </div>
      <input
        v-model="statusData.name"
        type="text"
        placeholder="Type here"
        class="itbkk-status-name input input-bordered w-full bg-white"
      />
      <div class="label">
        <!-- ? Error Text -->
        <span v-if="Errortext.name !== ''" class="label-text-alt text-error">{{
          Errortext.name
        }}</span>
      </div>
      <!-- * description -->
      <label class="form-control basis-3/4">
        <div class="label">
          <!-- ? Head -->
          <span class="label-text">Description</span>
        </div>
        <textarea
          v-model="statusData.description"
          class="itbkk-status-description textarea textarea-bordered h-72 bg-white"
          placeholder="Bio"
        ></textarea>
        <div class="label">
          <!-- ? Error Text -->
          <span
            v-if="Errortext.description !== ''"
            class="label-text-alt text-error"
          >
            {{ Errortext.description }}</span
          >
        </div>
      </label>
    </label>
    <hr />
    <div class="flex flex-row-reverse gap-4 mt-5">
      <button
        class="itbkk-button-cancel btn btn-outline btn-error basis-1/6"
        @click="sendCloseModal()"
      >
        Cancel
      </button>
      <button
        class="itbkk-button-confirm btn btn-outline btn-success basis-1/6"
        :disabled="!canSave"
        :class="!canSave ? 'disabled' : ''"
        @click="fetchData()"
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
 
<style scoped>

</style>