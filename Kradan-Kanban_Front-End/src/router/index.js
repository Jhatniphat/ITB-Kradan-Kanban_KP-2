import { createRouter, createWebHistory } from "vue-router"
import HomeView from "../views/HomeView.vue"
import TaskList from "../views/TasklistView.vue"
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: "/",
    //   name: "home",
    //   component: HomeView,
    // },
    {
      path: "/",
      redirect: { path: "/task" },
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/AboutView.vue"),
    },
    {
      path: "/task",
      name: "tasklist",
      component: TaskList,
    },
    {
      path: "/task/:id",
      component: () => import("../views/TasklistView.vue"),
    },
    {
      path: "/status",
      name: "statuslist",
      component: () => import("../views/StatusListView.vue"),
    },
    {
      path: "/status/:id", // Define a route for editing task
      component: () => import("../views/StatusListView.vue"),
    },
  ],
})

export default router
