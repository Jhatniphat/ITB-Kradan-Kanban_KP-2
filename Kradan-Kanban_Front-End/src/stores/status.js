import { getAllStatus } from "@/lib/fetchUtils";
import { defineStore } from "pinia";

export const useStatusStore = defineStore('status', {
    state: () => ({
        status: [],
    }),
    actions: {
        async getAllStatus() {
        let items
        try {
            items = await getAllStatus()
            this.status = await items
            return this.status
        } catch (error) {
            console.log(error)
        }
        },
    addStoreStatus(newStatus) {
        this.status.push(newStatus)
    }
    }
})