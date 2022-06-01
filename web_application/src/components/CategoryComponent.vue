<template>
  <section id="categories">
    <h2>Önnek ajánljuk</h2>
    <div class="categories-container container">
      <div v-for="category in topCategories" v-bind:key="category.id" class="categories-type ">
        <div class="img-container">
          <img :src=category.imgUrl alt="error">
          <div class="img-content">
            <h3>{{ category.name }}</h3>
            <router-link :to="{name: 'Books', params: { category_id: category.id }}"
                         class="btn btn-primary">
              Érdekel
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import {BASE_URL} from "@/service/url";

export default {
  data() {
    return {
      topCategories: []
    }
  },
  created() {
    axios
        .get(BASE_URL + "/books/categories")
        .then(response => {
          let categories = response.data.filter(category => category.imgUrl !== undefined)
          let index = 0;
          do {
            let actualData = categories[Math.floor(Math.random() * categories.length)];
            if (this.topCategories.filter(category => category.id === actualData.id).length !== 0) {
              continue
            }
            this.topCategories[index++] = actualData
          } while (this.topCategories.length < 3)
        })
        .catch(error => console.log(error));
  },

};
</script>

<style>

#categories {
  padding: 5rem 0 10rem 0;
}

#categories h2 {
  text-align: center;
  font-size: 2.5rem;
  font-weight: 400;
  margin-bottom: 40px;
  text-transform: uppercase;
  color: #555;
}

.categories-container {
  display: flex;
  justify-content: space-between;
}

.categories-container img {
  display: block;
  width: 100%;
  max-width: 340px;
  margin: auto;
  max-height: 210px;
  object-fit: cover;
  object-position: center;
}

.img-container {
  margin: 0 1rem;
  position: relative;
}

.img-content {
  position: absolute;
  top: 70%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
  z-index: 2;
  text-align: center;
  transition: all 0.3s ease-in-out 0.1s;
}

.img-content h3 {
  color: #fff;
  font-size: 2.2rem;
}

.img-content a {
  font-size: 1.2rem;
}

.img-container::after {
  content: "";
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.871);
  opacity: 0;
  z-index: 1;

  transform: scaleY(0);
  transform-origin: 100% 100%;
  transition: all 0.3s ease-in-out;
}

.img-container:hover::after {
  opacity: 1;
  transform: scaleY(1);
}

.img-container:hover .img-content {
  opacity: 1;
  top: 40%;
}

.categories-menu-heading {
  text-align: center;
  font-size: 3.4rem;
  font-weight: 400;
  color: #666;
}

.categories-menu-container {
  display: flex;
  flex-wrap: wrap;
  padding: 50px 0px 30px 0px;
}

.categories-menu-container img {
  display: block;
  width: 250px;
  height: 250px;
  border-radius: 50%;
  object-fit: cover;
  object-position: center;
}

.categories-menu-item {
  display: flex;
  flex: 1 1 600px;
  justify-content: space-evenly;
  margin-bottom: 3rem;
}

.categories-description {
  margin: auto 1.5rem;
}

.font-title {
  font-size: 1.8rem;
  font-weight: 400;
  color: #444;
}

.categories-description p {
  font-size: 1.4rem;
  color: #555;
  font-weight: 500;
}

.categories-description .categories-price {
  color: #117964;
  font-weight: 700;
}

.categories-type {
  margin-bottom: 4rem;
}

.categories-type {
  box-shadow: 5px 5px 10px 0 #aaa;
}

.img-container {
  margin: 0;
}

</style>