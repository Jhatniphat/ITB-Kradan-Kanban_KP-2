import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getAllTasks } from '@/lib/fetchUtils'

export const useTaskStore = defineStore('tasks', {
  state: () => ({
    tasks: [],
  }),
  actions: {
    async getAllTasks() {
      if (this.tasks.length > 0) return tasks
      else {
        let items
        try {
          items = await getAllTasks()
          this.tasks = await items
          if (typeof (items) !== Number) {
            return this.tasks
          }
        } catch (error) {
          console.log(error)
        }

      }
    }
  }

  // return { count, doubleCount, increment }
})
