<script setup>
import { ref, watch } from "vue";
import { getStatusById, editStatus } from "../../lib/fetchUtils";
import router from "@/router";

const canSave = ref(false);
const loading = ref(false);
const statusDetail = ref(null);
const originalsDetail = ref(null);
const error = ref(null);
const emit = defineEmits(["closeModal"]);
const props = defineProps({
  statusId: {
    type: Number,
    require: true,
  },
});
// const Errortext = ref({
//   title: "",
//   description: "",
//   assignees: "",
// })

watch(() => props.statusId, fetchData, { immediate: true });

watch(
  statusDetail,
  (newVal) => {
    if (
      !loading.value &&
      JSON.stringify(newVal) !== JSON.stringify(originalsDetail.value)
    ) {
      canSave.value = true;
    } else {
      canSave.value = false;
    }
  },
  { deep: true }
);

async function fetchData(id) {
  error.value = statusDetail.value = null;
  loading.value = true;
  try {
    const originalstatusDetails = await getStatusById(id);
    originalsDetail.value = { ...originalstatusDetails };
    statusDetail.value = { ...originalstatusDetails };
    if (statusDetail.value == 404) {
      router.push("/status");
    }
  } catch (err) {
    error.value = err.toString();
  } finally {
    loading.value = false;
    console.table(statusDetail.value);
  }
}

async function saveTask() {
  loading.value = true;
  let res;
  try {
    delete statusDetail.value.id;
    delete statusDetail.value.createdOn;
    delete statusDetail.value.updatedOn;
    res = await editStatus(props.statusId, statusDetail.value);
    statusDetail.value = res;
    console.log(res);
  } catch (error) {
    console.log(error);
  } finally {
    loading.value = false;
    router.push("/status");
    emit("closeModal", res);
  }
}

function sendCloseModal() {
  router.push("/status");
  emit("closeModal", null);
}
</script>

<template>
  <div class="flex flex-col p-5 text-black bg-slate-50 rounded-lg w-full">
    <label class="form-control w-full">
      <div class="label">
        <h1 class="m-2 text-3xl font-bold" v-if="loading === true">
          Loading Data For StatusId = {{ props.statusId }}
        </h1>
        <div v-if="loading === false && error === null">
          <h1 class="m-2 mt-0 text-2xl font-bold text-wrap break-all">
            Edit Status
          </h1>
          <hr />
          <span class="label-text">Title</span>
        </div>
        <hr />
      </div>
      <!-- Problem v-model="statusDetail.name" below here -->
      <input
        type="text"
        placeholder="Type here"
        class="itbkk-title input input-bordered w-full bg-white"
      />
    </label>

    <!-- * description -->
    <div
      class="flex mb-5 mx-auto flex-col w-full"
      v-if="loading === false && error === null"
    >
      <div class="flex flex-row gap-3">
        <label class="form-control basis-3/4">
          <div class="label">
            <!-- ? Head -->
            <span class="label-text">Description</span>
          </div>
          <textarea
            v-model="statusDetail.description"
            class="itbkk-description textarea textarea-bordered h-72 bg-white"
            placeholder="No Description Provided"
            :class="
              statusDetail.description === '' ? 'italic text-gray-600' : ''
            "
            >{{
              statusDetail.description == "" ||
              statusDetail.description === null
                ? "No Description Provided"
                : statusDetail.description
            }}</textarea
          >
        </label>
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
              {{ statusDetail.createdOn }}
            </h1>
          </div>
          <div class="flex flex-row justify-around">
            <h1 class="font-bold">Updated On</h1>
            <h1 class="itbkk-updated-on font-semibold">
              {{ statusDetail.updatedOn }}
            </h1>
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
          class="itbkk-button-cancel btn btn-outline btn-error basis-1/6"
          @click="sendCloseModal()"
        >
          Cancel
        </button>

        <button
          class="itbkk-button-confirm btn btn-outline btn-success basis-1/6"
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
  </div>
</template>

<style scoped></style>
