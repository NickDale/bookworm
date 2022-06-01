<template>
  <section id="showcase">
    <div class="showcase-container">
      <h1 id="home" class="main-title"></h1>
    </div>
  </section>
  <div class="event-card">
    <div class="card-header">
    </div>
    <div class="card-body">

      <div class="row">
        <div class="col-md-3">
          <img :src="book.img_link" class="img-fluid"/>
        </div>

        <div class="col-md-6">
          <div class="row">
            <div class="col-md-6">
              <h3 class="card-title">{{ book.title }}</h3>
              <div class="status">
                <span><b>Státusz:</b> {{ statusColorR ? 'Csak helyben olvasható' : 'Kölcsönözhető' }}</span>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="status">
                <span><b>Kategória:</b> {{ book.category.name }} </span>
              </div>
              <div class="status">
                <span><b>Szerző:</b> {{ book.authors_string }} </span>
              </div>
            </div>
          </div>

          <br/>
          <br/>
          <div class="row">
            <div class="col-md-8">
              <div>
                <span><b>Rövid leírás:</b> </span>
              </div>
              <div class="status">
                <span>{{ book.short_description }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import axios from "axios";
import {BASE_URL} from "@/service/url";

export default {
  data() {
    return {
      book: null
    };
  },
  props: ["id"],
  created() {
    if (this.id === undefined || this.id === null) {
      window.location.replace("/books");
    }
    axios
        .get(BASE_URL + "/books/" + this.id)
        .then(resp => this.book = resp.data)
        .catch(err => alert(err.response.data.message));
  }
};
</script>

<style scoped>

.status {
  display: flex;
  justify-content: left;
}

img {
  display: flex;
  justify-content: center;
  margin-top: 5%;
  max-width: 600px;
  height: 500px;
}
</style>