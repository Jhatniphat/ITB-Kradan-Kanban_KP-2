import {getAllStatus, getAllTasks} from "@/lib/fetchUtils";
import {defineStore} from "pinia";
import {useTaskStore} from "@/stores/task.js";

export const useStatusStore = defineStore('status', {
    state: () => ({
        status: [],
        limit : 10,
        limitEnable : true
    }),
    actions: {
        // async getAllStatus() {
        //     let items
        //     try {
        //         items = await getAllStatus()
        //         this.status = await items
        //         return this.status
        //     } catch (error) {
        //         console.log(error)
        //     }
        // },
        async getAllStatus() {
            if (this.status.length > 0) return this.status;
            else {
                let items;
                try {
                    items = await getAllStatus();
                    this.status = await items;
                    if (typeof items !== Number) {
                        return this.status;
                    }
                } catch (error) {
                    console.log(error);
                }
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
        },
        countStatus(status){
            // return this.status.filter( S => S === status).length
            return useTaskStore().tasks.filter((task) => task.status === status).length
        },
        // ? ไว้ใช้ตอนสร้าง task
        // ? ถ้า limitEnable : false > isLimit จะเป็น false ทั้งหมด
        getAllStatusWithLimit(){
            let limitStatus = []
            this.status.forEach( s => {
                if (s.name === 'No Status') limitStatus.push( { name : s.name , isLimit : false } )
                else limitStatus.push( { name : s.name , isLimit : this.limitEnable ? this.countStatus(s.name) >= this.limit : false } )
            }  )
            return limitStatus
        },
        getLimit(){
            return this.limit
        },
        setLimit(limit){
            this.limit = limit
        },
        setLimitEnable(limitEnable){
            this.limitEndble = limitEnable
        },
    }
})