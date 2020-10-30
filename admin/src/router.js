import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome"
import Course from "./views/admin/course";
import Chapter from "./views/admin/chapter"
import Section from "./views/admin/section"
import Category from "./views/admin/category";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [{
    path: "*",
    redirect: "/login",
  }, {
    path: "",
    redirect: "/login",
  }, {
    path: "/login",
    component: Login
  }, {
    path: "/",
    name: "admin",
    component: Admin,
    children: [{
      path: "welcome",
      name: "welcome",
      component: Welcome
    }, {
      path: "business/course",
      name: "business/course",
      component: Course
    }, {
      path: "business/chapter",
      name: "business/chapter",
      component: Chapter
    }, {
      path: "business/section",
      name: "business/section",
      component: Section
    }, {
      path: "business/category",
      name: "business/category",
      component: Category
    }]
  }]
})
