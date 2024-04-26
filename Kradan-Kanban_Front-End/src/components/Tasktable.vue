<script setup>
import { computed } from "vue";

defineEmits(["openModal"]);

const props = defineProps({
  task: {
    type: Object,
    default: {
      taskId: undefined,
      taskTitle: "",
      taskDescription: "",
      taskAssignees: "",
      taskStatus: "No status",
      createdOn: "",
      updatedOn: "",
    },
  },
});

const taskData = computed(() => props.task);
</script>

<template>
  <div>
    <table
      class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1"
    >
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
        <tr
          v-for="list in taskData"
          :key="list.taskId"
          class="hover:bg-slate-700"
        >
          <th>{{ list.taskId }}</th>
          <td>
            <button @click="$emit('openModal', true, list.taskId)">
              {{ list.taskTitle }}
            </button>
          </td>
          <td>{{ list.taskAssignees }}</td>
          <td>{{ list.taskStatus }}</td>
        </tr>
        <!-- Test
        <tr class="hover:bg-slate-700">
          <th>999</th>
          <td>
            <button @click="$emit('openModal', true)">ສ້າງຖານຂໍ້ມູນ</button>
          </td>
          <td>あなた、彼、彼女 (私ではありません)</td>
          <td>Test</td>
        </tr>
        <tr class="hover:bg-slate-700">
          <th>999</th>
          <td>
            <button @click="$emit('openModal', true)">เทส</button>
          </td>
          <td>sawasdee kub สวัสดี</td>
          <td>Test</td>
        </tr>
        <tr class="hover:bg-slate-700">
          <th>999</th>
          <td>
            <button @click="$emit('openModal', true)">Test</button>
          </td>
          <td></td>
          <td>Test</td>
        </tr> -->
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
