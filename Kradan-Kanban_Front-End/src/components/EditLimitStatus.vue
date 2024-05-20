<script setup>
import {computed, onMounted, ref} from "vue";
import {useStatusStore} from "@/stores/status.js";

import {toggleLimitStatus} from "@/lib/fetchUtils.js";


const statusStore = useStatusStore()
const limitStatusValue = ref({})
let oldLimitStatusValue = {isEnable: true, limit: 10}
const emit = defineEmits(["closeModal"])

const canConfirmBtn = ref(true)
const loading = ref(false)

onMounted(() => {
  limitStatusValue.value = {isEnable: statusStore.getLimitEnable(), limit: statusStore.getLimit()}
  oldLimitStatusValue = {...limitStatusValue.value}
})

// function EditLimitModal(openOrClose) {
//   if (openOrClose) {
//     limitStatusValue.value = { isEnable: statusStore.getLimitEnable(), limit: statusStore.getLimit() }
//     showEditLimit.value = true
//   } else {
//     statusStore.setLimitEnable(limitStatusValue.value.isEnable)
//     statusStore.setLimit(limitStatusValue.value.limit)
//     showEditLimit.value = false
//     let overStatus = statusStore.getOverStatus()
//     if (overStatus.length > 0) {
//       showToast({status: "error", msg: `Has Over Limit Status : ${overStatus.join(" , ")}`}, 5000);
//     }
//   }
// }

// async function ConfirmEdit() {
//   statusStore.setLimitEnable(limitStatusValue.value.isEnable)
//   statusStore.setLimit(limitStatusValue.value.limit)
//   let overStatus = statusStore.getOverStatus()
//   if (overStatus.length > 0) {
//     if (overLimitTasks.value.length > 0) {
//       let count = 0
//       for (const task of overLimitTasks.value) {
//         task.status = task.newStatus
//         delete "newStatus" in task
//         try {
//           const res = await editTask(task.id, task)
//           if (typeof res === "object") {
//             taskStore.editStoreTask(task)
//           }
//         } catch (e) {
//           console.log(e)
//         } finally {
//           count++;
//           if (count >= overLimitTasks.value.length) {
//             loading.value = false
//             emit("closeModal")
//           }
//         }
//       }
//     } else {
//       canConfirmBtn.value = false
//       overLimitTasks.value = []
//       overStatus.forEach(status => {
//         taskStore.getTasksByStatus(status).forEach(overTask => {
//           overLimitTasks.value.push(overTask)
//         })
//       })
//       overLimitTasks.value.forEach(task => task.newStatus = task.status)
//     }
//   } else {
//     emit("closeModal", null)
//   }
// }

async function confirmEdit() {
  statusStore.setLimit(limitStatusValue.value.limit)
  if (limitStatusValue.value.isEnable !== statusStore.getLimitEnable()) {
    try {
      let res = await toggleLimitStatus()
      if (res === 200 || res === 204) {
        statusStore.setLimitEnable(limitStatusValue.value.isEnable)
      }
    } catch (e) {
      console.log(e)
    }

  }
  // statusStore.setLimitEnable(limitStatusValue.value.isEnable)
  let overStatus = statusStore.getOverStatus()
  if (overStatus.length > 0) {
    let overStatusObj = []
    overStatus.forEach((status) =>
        overStatusObj.push({name: status, task: statusStore.countStatus(status)})
    )
    emit("closeModal", overStatusObj)
  } else emit("closeModal", null)
}

// function canConfirm(tasks, oldTasks) {
//   let canOverStatus = statusStore.getCanOverStatus()
//   let limit = statusStore.getLimit()
//   canOverStatus.forEach(status => {
//         if (tasks.filter(task => task.newStatus === status.name).length > limit) {
//           canConfirmBtn.value = false;
//           return 0;
//         } else canConfirmBtn.value = true
//       }
//   )
// }

// watch(() => overLimitTasks.value, canConfirm, {deep: true})
const limitStatusValueError = computed(() => {
      return (limitStatusValue.value.limit > 30 || limitStatusValue.value.limit < 0) ? `Limit Be only between 0 and 30!!` : ''
    }
)

function closeEdit() {
  statusStore.setLimitEnable(oldLimitStatusValue.isEnable)
  statusStore.setLimit(oldLimitStatusValue.limit)
  limitStatusValue.value = {...oldLimitStatusValue}
  emit("closeModal", null)
}
</script>

<template>
  <div class="itbkk-modal-task flex flex-col gap-3 p-5 text-black bg-slate-50 rounded-lg w-full m-auto">
    <h1>Limit Status</h1>
    <hr>
    <div class="form-control w-fit">
      <label class="cursor-pointer label">
        <input type="checkbox" class="toggle toggle-primary" v-model="limitStatusValue.isEnable"/>
        <span class="label-text pl-1">Enable Limit</span>
      </label>
    </div>
    <div class="form-control">
      <label class="label text-error">Limit {{ limitStatusValueError }}</label>
      <input type="number" class="input text-base" v-model="limitStatusValue.limit">
    </div>
    <!--    <div class="flex flex-col gap-2" v-if="overLimitTasks.length > 0">-->
    <!--      <h2>Oh ,We have Status that over limit : {{ statusStore.getOverStatus().join(" , ") }}</h2>-->
    <!--      <p>Please make progress or finish tasks in that status and try again.</p>-->
    <!--      <div class="overflow-x-scroll max-h-56">-->
    <!--        <table class="table table-zebra table-pin-rows">-->
    <!--          <thead>-->
    <!--          <tr>-->
    <!--            <th>No.</th>-->
    <!--            <th>Title</th>-->
    <!--            <th>Assignees</th>-->
    <!--            <th>Old Status</th>-->
    <!--            <th>New Status</th>-->
    <!--          </tr>-->
    <!--          </thead>-->
    <!--          <tbody>-->
    <!--          <tr v-for="(task , index) in overLimitTasks">-->
    <!--            <td>{{ index + 1 }}</td>-->
    <!--            <td>{{ task.title }}</td>-->
    <!--            <td>{{ task.assignees }}</td>-->
    <!--            <td>{{ task.status }}</td>-->
    <!--            <td>-->
    <!--              <select class="select select-bordered" v-model="task.newStatus">-->
    <!--                <option v-for="status in statusStore.getAllStatusWithLimit()" :value="status.name">{{ status.name }}-->
    <!--                  {{ status.isLimit ? '(max)' : '' }}-->
    <!--                </option>-->
    <!--              </select>-->
    <!--            </td>-->
    <!--          </tr>-->
    <!--          </tbody>-->
    <!--        </table>-->
    <!--      </div>-->
    <!--    </div>-->
    <div class="flex flex-row-reverse gap-4 mt-5">
      <button class="btn btn-outline btn-success" @click="confirmEdit"
              :disabled="limitStatusValueError!=='' || !canConfirmBtn">{{ loading ? "" : "Confirm" }}
        <span
            class="loading loading-spinner text-success"
            v-if="loading"
        ></span>
      </button>
      <button class="btn btn-outline btn-error" @click="closeEdit">Close</button>
    </div>
  </div>
</template>

<style scoped>

</style>