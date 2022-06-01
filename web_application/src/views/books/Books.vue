<template>
  <section id="showcase" class="">
    <div class="showcase-container">
      <h1 id="home" class="main-title">Aktuális kínálatunk</h1>
    </div>
  </section>

  <div class="container">
    <div class="row">
      <BookComponent v-for="book in books" :key="book.book_id" :book="book"/>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {BASE_URL} from "@/service/url";
import BookComponent from "@/components/BookComponent";

export default {
  components: {BookComponent},
  data() {
    return {
      books: [],
    };
  },
  props: ["category_id"],
  created() {
    let url = BASE_URL + "/books/";
    if (this.category_id !== undefined && this.category_id !== "") {
      url += "?search=categoryId:" + this.category_id;
    }
    axios
        .get(url)
        .then((response) => {
          console.log(response.data);
          this.books = response.data;
        })
        .catch((error) => console.log(error));
  },
};
</script>

<style>
</style>