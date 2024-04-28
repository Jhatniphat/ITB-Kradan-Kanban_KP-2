<script setup>
import { computed, onBeforeMount, onMounted, ref } from "vue";
import { getAllTasks } from "@/lib/fetchUtils";

// const allTask = ref(undefined)
// const getData = await getAllTasks()
// allTask.value = await getData
// onBeforeMount( async ()=> {
//   const allTasks = await getAllTasks()
// })

console.table(await getAllTasks())
defineEmits(["openModal"]);
const props = defineProps( {
  Tasks : { 
    type : Array
  }
}
)



</script>

<template>
  <div>
    <table
      class="table table-lg table-pin-rows table-pin-cols w-3/4 font-semibold mx-auto text-center text-base rounded-lg border-2 border-slate-500 border-separate border-spacing-1">
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
        <tr>test</tr>
        <!-- <tr v-if="allTasks === undefined">
          <td colspan="4">Waiting For Data</td>
        </tr>
        <tr v-if="allTasks !== undefined" v-for="task in allTasks" :key="task.taskId" class="hover:bg-slate-700">
          <th>{{ task.taskId }}</th>
          <td>
            <button @click="$emit('openModal', task.taskId)">
              {{ task.taskTitle }}
            </button>
          </td>
          <td>{{ task.taskAssignees }}</td>
          <td>{{ task.taskStatus }}</td>
        </tr> -->
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
