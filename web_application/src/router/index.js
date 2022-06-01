import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Books from '../views/books/Books.vue'
import Details from '../views/books/Details.vue'
import Registration from "@/views/Registration";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/books',
    name: 'Books',
    component: Books,
    props: true
  },
  {
    path: '/details',
    name: 'Details',
    component: Details,
    props: true
  },
  {
    path: '/registration',
    name: 'Registration',
    component: Registration
  },
  {
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: Home
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
