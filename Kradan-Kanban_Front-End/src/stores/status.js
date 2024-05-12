import {getAllStatus} from "@/lib/fetchUtils";
import {defineStore} from "pinia";

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
        },
        deleteStoreStatus(statusToDelete) {
            const index = this.status.findIndex(status => status.id === statusToDelete.id);
            if (index !== -1) {
                this.status.splice(index, 1);
            }
        },
        editStoreStatus(updatedStatus) {
            const index = this.status.findIndex(status => status.id === updatedStatus.id)
            if (index !== -1) {
                this.status.splice(index, 1, updatedStatus)
            }
        },
        findById(id) {
            return this.status.find(status => status.id === id)
        }
    }
})