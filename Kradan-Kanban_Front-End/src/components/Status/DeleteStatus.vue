<script setup>
import { getAllStatus, deleteStatus, transferStatus } from "@/lib/fetchUtils";
import { ref } from "vue";

const emit = defineEmits(["closeModal"]);
const props = defineProps({
  deleteId: {
    type: Number,
    require: true,
  },
  deleteTitle: {
    type: String,
    require: true,
  },
});

const transfer = ref(false);
const loading = ref(false);
const statusList = ref(null);
const error = ref(null);
const newId = ref(null);

// watch(() => props.deleteId, { immediate: true });

// onMounted(() => {
//   fetchData();
// });

const deleteThisStatus = async () => {
  loading.value = true;
  let res;
  try {
    // Check if the status is already in use
    const inUse = await isStatusInUse(props.deleteId);
    if (inUse) {
      // If status is in use, switch to transferring status
      transfer.value = true;
      return;
    }
    // If not in use, proceed with deletion
    res = await deleteStatus(props.deleteId);
  } catch (error) {
    console.log(error);
  } finally {
    emit("closeModal", res);
  }
};

const closeThisModal = () => {
  emit("closeModal", null);
};

const isStatusInUse = async (statusId) => {
  statusList = await getAllStatus();
  return statusList.some((status) => status.id === statusId && status.inUse);
};

const transferTheStatus = async (newId) => {
  let res;
  try {
    res = await transferStatus(props.deleteId, newId);
  } catch (error) {
    console.log(error);
  } finally {
    emit("closeModal", res);
  }
};
</script>

<template>
  <div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg w-full">
    <!-- Delete Modal -->
    <div v-if="!transfer">
      <h1 class="m-2 pb-4 text-2xl font-bold">Delete a Satus</h1>
      <hr />
      <h1 class="itbkk-message font-semibold text-xl p-8">
        Do you want to delete the "{{ deleteTitle }}" Status?
      </h1>
      <hr />
      <div class="flex flex-row-reverse gap-4 mt-5">
        <button
          @click="closeThisModal"
          class="itbkk-button-cancel btn btn-outline btn-error basis-1/6"
        >
          Close
        </button>
        <button
          @click="deleteThisStatus()"
          class="itbkk-button-confirm btn btn-outline btn-success basis-1/6"
        >
          {{ loading ? "" : "Confirm" }}
          <span
            class="loading loading-spinner text-success"
            v-if="loading"
          ></span>
        </button>
      </div>
    </div>
    <!-- Tranfers Modal -->
    <div v-if="transfer">
      <h1 class="m-2 pb-4 text-2xl font-bold">Transfer a Satus</h1>
      <hr />
      <h1 class="itbkk-message font-semibold text-xl p-8">
        There is some task associated with the "{{ deleteTitle }}" Status?
        Tranfer to
      </h1>
      <label class="form-control w-full max-w-xs">
        <div class="label">
          <span class="label-text">Status</span>
        </div>
        <select
          v-model="newId"
          class="itbkk-status select select-bordered bg-white"
        >
          <!-- <option value="No Status" selected>No Status</option> -->
          <option v-for="status in statusList" :key="status.id" :value="status">
            {{ status.name }}
          </option>
        </select>
      </label>
      <hr />
      <div class="flex flex-row-reverse gap-4 mt-5">
        <button
          @click="closeThisModal"
          class="itbkk-button-cancel btn btn-outline btn-error basis-1/6"
        >
          Close
        </button>
        <button
          @click="transferTheStatus(newId)"
          class="itbkk-button-confirm btn btn-outline btn-success basis-1/6"
        >
          {{ loading ? "" : "Tranfer" }}
          <span
            class="loading loading-spinner text-success"
            v-if="loading"
          ></span>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
